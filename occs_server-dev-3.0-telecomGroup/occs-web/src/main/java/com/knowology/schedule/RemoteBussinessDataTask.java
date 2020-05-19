package com.knowology.schedule;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.GeneralSecurityException;
import java.security.Permission;
import java.security.PermissionCollection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.poifs.crypt.Decryptor;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.knowology.excelVo.BussinessHall;
import com.knowology.excelVo.BussinessHallResult;
import com.knowology.excelVo.BussinessHallSample;
import com.knowology.excelVo.GovernDelivery;
import com.knowology.excelVo.GovernDeliveryResult;
import com.knowology.excelVo.GovernDeliverySample;
import com.knowology.service.BussinessDataService;
import com.knowology.thread.BussinessDataInnerAsyncService;
import com.knowology.util.DateUtil;
import com.knowology.util.EasyExcelUtil;

/**
 * 做一个定时任务，用来处理三张excel的导入数据处理以及导出处理
 * 
 * @author xullent
 */
@Component
@ConditionalOnProperty(prefix = "occs.schedule.remote-bussinessData-task", value = "enable", havingValue = "true")
public class RemoteBussinessDataTask {

	private static final Logger logger = LoggerFactory.getLogger(RemoteBussinessDataTask.class);
	@Autowired
	private BussinessDataInnerAsyncService taskDataInnerAsyncService;
	@Autowired
	private BussinessDataService bussinessDataService;

	private final String JOB_ONE = "营业厅服务满意率";
	private final String JOB_TWO = "政企交付满意度";
	private final String JOB_THREE = "智慧家庭交付满意率";

	private final String FILE_ONE = "YYTFM";
	private final String FILE_TWO = "ZQJFFM";
	private final String FILE_THREE = "ZHJTJM";
	private final String FILE_FOUR = "测评结果";
	private final String FILE_FIVE = "样本接触状态";

	/**
	 * 文件读取的路径，暂时先写成某个固定位置每天取一次excel数据
	 */
	@Value("${fileExcel.readPath}")
	private String fileReadPath;

	/**
	 * 文件生成的路径，暂时先写成某个固定位置每天生成一次
	 */
	@Value("${fileExcel.writePath}")
	private String fileWirtePath;

	@Value("${fileExcel.password}")
	private String filePassword;

	/**
	 * 执行数据解析入库,在凌晨进行数据的解析，早于任务的入库
	 * 
	 * @throws SchedulerException
	 */
	@Scheduled(cron = "1 0 2 * * ?")
	public void executeReadEexcel() throws IOException, GeneralSecurityException, SchedulerException {
		logger.info("【用来处理三张表的定时任务开始】");
		// 先做一个本地的示例,文本统一格式 前缀yyyyMMdd.xml (可更改)
		File file1 = new File(fileReadPath + FILE_ONE + DateUtil.dateFormat(new Date(), "yyyyMMdd") + ".xlsx");
		File file2 = new File(fileReadPath + FILE_TWO + DateUtil.dateFormat(new Date(), "yyyyMMdd") + ".xlsx");
		File file3 = new File(fileReadPath + FILE_THREE + DateUtil.dateFormat(new Date(), "yyyyMMdd") + ".xlsx");
		if (file1.exists()) {
			bussinessDataService.insertBussinessHallData(judgeBussinesshallFile(file1));
		}
		if (file2.exists()) {
			bussinessDataService.insertGovernDeliverylData(judgeGovernDeliveryFile(file2, 2));
		}
		if (file3.exists()) {
			bussinessDataService.insertGovernDeliverylData(judgeGovernDeliveryFile(file3, 3));
		}
	}

	/**
	 * 营业厅服务满意率 ，只处理第一个sheet
	 * 
	 * @param file
	 * @return
	 */
	public static List<BussinessHall> judgeBussinessFile(File file) {
		List<BussinessHall> bussinessDataList = new ArrayList<>();
		List<Object> list = EasyExcelUtil.readExcel(file, BussinessHall.class, 1);
		for (Object o : list) {
			BussinessHall bussinessHall = (BussinessHall) o;
			// 设置一下标志，来自营业厅服务满意率
			bussinessHall.setFlag(1);
			bussinessHall.setCreateTime(new Date());
			bussinessDataList.add(bussinessHall);
		}
		return bussinessDataList;
	}

	/**
	 * 政企交付满意度 ，只处理第一个sheet
	 * 
	 * @param file
	 * @return
	 */
	public static List<GovernDelivery> judgeGovernDeliveryFile(File file, Integer flag) {
		List<GovernDelivery> governDeliveryArrayList = new ArrayList<>();
		List<Object> list = EasyExcelUtil.readExcel(file, GovernDelivery.class, 1);
		for (Object o : list) {
			GovernDelivery governDelivery = (GovernDelivery) o;

			// 设置一下标志，来自政企交付满意度(2)还是智慧家庭交付满意率(3)
			governDelivery.setFlag(flag);
			governDelivery.setCreateTime(new Date());
			governDeliveryArrayList.add(governDelivery);
		}
		return governDeliveryArrayList;
	}

	/**
	 * 导出结果文件到相应的目录
	 */
	@Scheduled(cron = "1 0 23 * * ?")
	public void executeWriteEexcel() throws Exception {
		logger.info("【导出三个任务的结果】");
		File dir = new File(fileWirtePath);
		if (!dir.exists()) {
			dir.mkdir();
		}
		writeBussinessHallExcel();
		writeGovernDeliveryExcel(FILE_TWO + "RESULT", JOB_TWO);
		writeGovernDeliveryExcel(FILE_THREE + "RESULT", JOB_THREE);
	}

	/**
     * 导出营业厅服务满意率表格,示例
     */
    private void writeBussinessHallExcel() throws Exception {
        List<BussinessHallResult> bussinessHallResults = bussinessDataService.selectBussinessHallResult(JOB_ONE);
        List<BussinessHallSample> bussinessHallSamples = bussinessDataService.selectBussinessHallSampleResult(JOB_ONE);
        if(bussinessHallResults == null || bussinessHallResults.size() ==0){
            //今日没有数据不再处理
            return;
        }else{
        	for(BussinessHallResult bres:bussinessHallResults){
        		if(bres!=null){
        			if(bres.getIntentfind()!=null && !"".equals(bres.getIntentfind())){
        				if(bres.getIntentfind()!=null){
        					String listString = bres.getIntentfind();
        					listString = listString.replace("[", "").replace("]", "").replace("\"", "");
							List<?> res = Arrays.asList(listString.split(","));
							setValues(bres,res);
        				}
        			}
        			if(bres.getContent()!=null && !"".equals(bres.getContent())){
        				JSONArray jsonArray = (JSONArray) JSONArray.parse(bres.getContent());
        				if(jsonArray.size()>0){
        					JSONObject bjson = (JSONObject) jsonArray.get(0);
        					bres.setAnswerBeginTime(bjson.getString("beginTime"));
        					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        					Date date = sdf.parse(bres.getAnswerBeginTime());
        					Calendar newTime = Calendar.getInstance();
        		            newTime.setTime(date);
        		            newTime.add(Calendar.SECOND,bres.getTalkTime());
        		            Date endTime=newTime.getTime();
        					bres.setAnswerEndTime(sdf.format(endTime));
        				}
            		}
        		}
        	}
        }
        //测评结果
        OutputStream outputStream = new FileOutputStream(fileWirtePath + FILE_FOUR + "RESULT" + DateUtil.dateFormat(new Date(),"yyyyMMdd") + ".xlsx");
        ExcelWriter writer = EasyExcelFactory.getWriter(outputStream);
        Sheet sheet=new Sheet(1,0,BussinessHallResult.class);
        sheet.setSheetName("测评结果");
        writer.write(bussinessHallResults,sheet);
        writer.finish();
        //样本接触状态
        outputStream = new FileOutputStream(fileWirtePath + FILE_FIVE + "RESULT" + DateUtil.dateFormat(new Date(),"yyyyMMdd") + ".xlsx");
        ExcelWriter writer1 = EasyExcelFactory.getWriter(outputStream);
        Sheet sheet1=new Sheet(1,0,BussinessHallSample.class);
        sheet1.setSheetName("样本接触状态");
        for(BussinessHallSample bussinessHallSample : bussinessHallSamples){
			setStatus(bussinessHallSample,bussinessHallSample.getCallStatus());
		}
        writer1.write(bussinessHallSamples,sheet1);
        writer1.finish();
        // 关闭流
        outputStream.close();
    }
    
    private void setStatus(BussinessHallSample bhas, String value) {
		switch (value) {
		case "甄别失败":
			value = "1";
			bhas.setStatus(value);
			break;
		case "拒接":
			value = "2";
			bhas.setStatus(value);
			break;
		case "空号":
			value = "3";
			bhas.setStatus(value);
			break;
		case "停机":
			value = "4";
			bhas.setStatus(value);
			break;
		case "其他":
			value = "5";
			bhas.setStatus(value);
			break;
		case "成功":
			value = "6";
			bhas.setStatus(value);
			break;
		case "关机":
			value = "8";
			bhas.setStatus(value);
			break;
		case "无人接听":
			value = "9";
			bhas.setStatus(value);
			break;
		case "占线":
			value = "10";
			bhas.setStatus(value);
			break;
		default:
			break;
		}
	}
    
	private void setValues(BussinessHallResult bres, List<?> values) {
		for (Object value : values) {
			switch (value.toString()) {
			case "同意测评":
				value = "1";
				bres.setAgreeOrNot(value.toString());
				break;
			case "办理过":
				value = "1";
				bres.setInstallReticle(value.toString());
				break;
			case "非常满意":
				value = "5";
				bres.setSatisfied(value.toString());
				break;
			case "满意":
				value = "4";
				bres.setSatisfied(value.toString());
				break;
			case "一般":
				value = "3";
				bres.setSatisfied(value.toString());
				break;
			case "不满意":
				value = "2";
				bres.setSatisfied(value.toString());
				break;
			case "非常不满意":
				value = "1";
				bres.setSatisfied(value.toString());
				break;
			case "业务办理时间长":
				value = "1";
				bres.setBussinesssLong(value.toString());
				break;
			case "排队时间长":
				value = "1";
				bres.setWaitingLong(value.toString());
				break;
			case "秩序混乱":
				value = "1";
				bres.setOutOfOder(value.toString());
				break;
			case "营业厅环境脏乱差":
				value = "1";
				bres.setEnvironBad(value.toString());
				break;
			case "服务态度差":
				value = "1";
				bres.setServiceAwful(value.toString());
				break;
			case "业务能力差":
				value = "1";
				bres.setBussinessAwful(value.toString());
				break;
			case "业务办理过程繁琐":
				value = "1";
				bres.setBussinessFussy(value.toString());
				break;
			case "设备故障":
				value = "1";
				bres.setEquipmentFailure(value.toString());
				break;
			case "业务办理速度快":
				value = "1";
				bres.setSpeed(value.toString());
				break;
			case "排队时间短":
				value = "1";
				bres.setShortQueuing(value.toString());
				break;
			case "秩序良好":
				value = "1";
				bres.setEunomy(value.toString());
				break;
			case "环境干净整洁":
				value = "1";
				bres.setCleanEnvironment(value.toString());
				break;
			case "态度好":
				value = "1";
				bres.setGoodAttitude(value.toString());
				break;
			case "业务熟练":
				value = "1";
				bres.setBusinessSkilled(value.toString());
				break;
			case "办理过程简单":
				value = "1";
				bres.setSimpleProcess(value.toString());
				break;
			case "顺利解决问题":
				value = "1";
				bres.setSmoothProblems(value.toString());
				break;
			default:
				break;
			}
		}
	}

	/**
	 * 导出政企交付满意度/智慧家庭交付满意率表格
	 */
	private void writeGovernDeliveryExcel(String exportName, String jobName) throws IOException {
		List<GovernDeliveryResult> governDeliveryResults = bussinessDataService.selectGovernDeliveryResult(jobName);
		List<GovernDeliverySample> bussinessHallSamples = bussinessDataService
				.selectGovernDeliverySampleResult(jobName);
		if (governDeliveryResults == null || governDeliveryResults.size() == 0) {
			// 今日没有数据不再处理
			return;
		}
		OutputStream outputStream = new FileOutputStream(
				fileWirtePath + exportName + DateUtil.dateFormat(new Date(), "yyyyMMdd") + ".xlsx");
		ExcelWriter writer = EasyExcelFactory.getWriter(outputStream);
		// 这是第一个sheet,对应相应的实体类
		Sheet sheet1 = new Sheet(1, 0, GovernDeliveryResult.class);
		sheet1.setSheetName("测评结果");
		// 这是第二个sheet,对应相应的实体类,有几个sheet对应截个，一并写入
		Sheet sheet2 = new Sheet(2, 0, GovernDeliverySample.class);
		sheet2.setSheetName("样本接触状态");

		writer.write(governDeliveryResults, sheet1);
		writer.write(bussinessHallSamples, sheet2);
		writer.finish();
	}

	/**
	 * 数据表检索，每日凌晨将今日数据抓入任务表中
	 */
	@Scheduled(cron = "1 0 10 * * ?")
	public void executeInnerDataToJobTask() throws SchedulerException {
		logger.info("【用来呼叫三张表的定时任务开始】");
		taskDataInnerAsyncService.innerDataToJobTask();
	}

	/**
	 * 对这三种任务的呼叫表中今日数据进行一次清除，不管呼叫完毕与否
	 */
	@Scheduled(cron = "1 0 22 * * ?")
	public void executeClearDataToJobTask() {
		taskDataInnerAsyncService.clearDataToJobTask();
	}

	/***
	 * 带密码的excel操作 --满意度
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	private List<BussinessHall> judgeBussinesshallFile(File file) throws IOException, GeneralSecurityException {
		String prefix = file.getPath().substring(file.getPath().lastIndexOf(".") + 1);

		Workbook workbook;
		List<BussinessHall> list = new ArrayList<>();
		InputStream inp = new FileInputStream(file);
		if (prefix.toUpperCase().equals("XLS")) {
			org.apache.poi.hssf.record.crypto.Biff8EncryptionKey.setCurrentUserPassword(filePassword);
			workbook = WorkbookFactory.create(inp);
			inp.close();
		} else {
			POIFSFileSystem pfs = new POIFSFileSystem(inp);
			inp.close();
			EncryptionInfo encInfo = new EncryptionInfo(pfs);
			Decryptor decryptor = Decryptor.getInstance(encInfo);
			decryptor.verifyPassword(filePassword);
			workbook = new XSSFWorkbook(decryptor.getDataStream(pfs));
		}

		org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);
		int startRowNum = sheet.getFirstRowNum() + 1;
		int endRowNum = sheet.getLastRowNum();
		for (int rowNum = startRowNum; rowNum <= endRowNum; rowNum++) {
			Row row = sheet.getRow(rowNum);
			if (row == null)
				continue;
			BussinessHall bussinessHall = new BussinessHall();
			bussinessHall.setRecordId(row.getCell(0).toString());
			bussinessHall.setProvince(row.getCell(1).toString());
			bussinessHall.setCity(row.getCell(2).toString());
			bussinessHall.setAreaCode(row.getCell(3).toString());
			row.getCell(4).setCellType(CellType.STRING);
			bussinessHall.setPhoneNumber(row.getCell(4).toString());
			bussinessHall.setServiceType(row.getCell(5).toString());
			bussinessHall.setProduceType(row.getCell(6).toString());
			bussinessHall.setSalesName((row.getCell(7) == null ? "" : row.getCell(7)).toString());
			bussinessHall.setSalesLevel((row.getCell(8) == null ? "" : row.getCell(8)).toString());
			bussinessHall.setSalesType((row.getCell(9) == null ? "" : row.getCell(9)).toString());
			bussinessHall.setCompleTime(row.getCell(10).getDateCellValue());
			bussinessHall.setFlag(1);
			bussinessHall.setCreateTime(new Date());
			list.add(bussinessHall);
		}
		return list;
	}

	/**
	 * 解决excel数字科学计算的问题
	 * 
	 * @param d
	 * @return
	 */
//	private String getRealStringValueOfDouble(Double d) {
//		String doubleStr = d.toString();
//		boolean b = doubleStr.contains("E");
//		int indexOfPoint = doubleStr.indexOf('.');
//		if (b) {
//			int indexOfE = doubleStr.indexOf('E');
//			BigInteger xs = new BigInteger(doubleStr.substring(indexOfPoint + BigInteger.ONE.intValue(), indexOfE));
//			int pow = Integer.valueOf(doubleStr.substring(indexOfE + BigInteger.ONE.intValue()));
//			int xsLen = xs.toByteArray().length;
//			int scale = xsLen - pow > 0 ? xsLen - pow : 0;
//			doubleStr = String.format("%." + scale + "f", d);
//		} else {
//			java.util.regex.Pattern p = Pattern.compile(".0$");
//			java.util.regex.Matcher m = p.matcher(doubleStr);
//			if (m.find()) {
//				doubleStr = doubleStr.replace(".0", "");
//			}
//		}
//		return doubleStr;
//	}

	static {
		removeCryptographyRestrictions();
	}

	private static void removeCryptographyRestrictions() {
		if (!isRestrictedCryptography()) {
			return;
		}
		try {
			/*
			 * Do the following, but with reflection to bypass access checks:
			 * 
			 * JceSecurity.isRestricted = false;
			 * JceSecurity.defaultPolicy.perms.clear();
			 * JceSecurity.defaultPolicy.add(CryptoAllPermission.INSTANCE);
			 */
			final Class<?> jceSecurity = Class.forName("javax.crypto.JceSecurity");
			final Class<?> cryptoPermissions = Class.forName("javax.crypto.CryptoPermissions");
			final Class<?> cryptoAllPermission = Class.forName("javax.crypto.CryptoAllPermission");

			Field isRestrictedField = jceSecurity.getDeclaredField("isRestricted");
			isRestrictedField.setAccessible(true);
			setFinalStatic(isRestrictedField, true);
			isRestrictedField.set(null, false);

			final Field defaultPolicyField = jceSecurity.getDeclaredField("defaultPolicy");
			defaultPolicyField.setAccessible(true);
			final PermissionCollection defaultPolicy = (PermissionCollection) defaultPolicyField.get(null);

			final Field perms = cryptoPermissions.getDeclaredField("perms");
			perms.setAccessible(true);
			((Map<?, ?>) perms.get(defaultPolicy)).clear();

			final Field instance = cryptoAllPermission.getDeclaredField("INSTANCE");
			instance.setAccessible(true);
			defaultPolicy.add((Permission) instance.get(null));
		} catch (final Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	static void setFinalStatic(Field field, Object newValue) throws Exception {
		field.setAccessible(true);

		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

		field.set(null, newValue);
	}

	private static boolean isRestrictedCryptography() {
		// This matches Oracle Java 7 and 8, but not Java 9 or OpenJDK.
		final String name = System.getProperty("java.runtime.name");
		final String ver = System.getProperty("java.version");
		return name != null && name.equals("Java(TM) SE Runtime Environment") && ver != null
				&& (ver.startsWith("1.7") || ver.startsWith("1.8"));
	}
}
