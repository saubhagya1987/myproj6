package vn.com.unit.fe_credit.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.entity.FileMeta;
import vn.com.unit.fe_credit.utils.Utils;
import vn.com.unit.fe_credit.utils.ajax.ReturnObject;
import vn.com.unit.utils.CompressJPEGFile;
import vn.com.unit.utils.ConvertVideo;

@Controller
@RequestMapping("/ajax")
public class FileController {

	@Autowired
	SystemConfig systemConfig;

	LinkedList<FileMeta> files = new LinkedList<FileMeta>();

	FileMeta fileMeta = null;

	private static final Logger logger = LoggerFactory.getLogger(FileController.class);

	/***************************************************
	 * URL: /rest/controller/upload upload(): receives files
	 * 
	 * @param request
	 *            : MultipartHttpServletRequest auto passed
	 * @param response
	 *            : HttpServletResponse auto passed
	 * @return LinkedList<FileMeta> as json format
	 ****************************************************/
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody LinkedList<FileMeta> upload(MultipartHttpServletRequest request, HttpServletResponse response) {

		// 1. build an iterator
		Iterator<String> itr = request.getFileNames();
		MultipartFile mpf = null;

		// 2. get each file
		while (itr.hasNext()) {

			// 2.1 get next MultipartFile
			mpf = request.getFile(itr.next());

			// 2.2 if files > 10 remove the first from the list
			if (files.size() >= 10)
				files.pop();

			// 2.3 create new fileMeta
			fileMeta = new FileMeta();
			fileMeta.setFileName(mpf.getOriginalFilename());
			fileMeta.setFileSize(mpf.getSize());
			fileMeta.setFileType(mpf.getContentType());
			fileMeta.setPhysicalFileName(mpf.getOriginalFilename() + (new Date()).getTime());

			try {
				fileMeta.setBytes(mpf.getBytes());
				logger.debug("temp path: {}",
						systemConfig.getConfig(SystemConfig.TEMP_FOLDER) + System.getProperty("file.separator") + mpf.getOriginalFilename());
				// copy file to local disk (make sure the path
				// "e.g. D:/temp/files" exists)
				FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(
						systemConfig.getConfig(SystemConfig.TEMP_FOLDER) + System.getProperty("file.separator") + fileMeta.getPhysicalFileName()));

			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error(e + ":" + e.getMessage());
				e.printStackTrace();
			}
			// 2.4 add to files
			files.add(fileMeta);

		}

		// result will be like this
		// [{"fileName":"app_engine-85x77.png","fileSize":"8 Kb","fileType":"image/png"},...]
		return files;

	}

	@RequestMapping(value = "/uploadDoc", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	// public @ResponseBody FileMeta uploadDocument(MultipartHttpServletRequest
	// request, HttpServletResponse response) {
	public @ResponseBody String uploadDocument(MultipartHttpServletRequest request, HttpServletResponse response) {
		// 1. build an iterator
		Iterator<String> itr = request.getFileNames();
		MultipartFile mpf = null;

		// 2. get each file
		if (itr.hasNext()) {
			// 2.1 get next MultipartFile
			mpf = request.getFile(itr.next());

			// 2.2 if files > 10 remove the first from the list
			if (files.size() >= 10)
				files.pop();

			// 2.3 create new fileMeta
			fileMeta = new FileMeta();
			fileMeta.setFileName(mpf.getOriginalFilename());
			fileMeta.setFileSize(mpf.getSize());
			fileMeta.setFileType(mpf.getOriginalFilename().substring(mpf.getOriginalFilename().lastIndexOf('.') + 1));
			fileMeta.setPhysicalFileName(Utils.convertNewFileName(mpf.getOriginalFilename().substring(0, mpf.getOriginalFilename().lastIndexOf('.')))
					+ (new Date()).getTime() + "." + fileMeta.getFileType());
			try {
				fileMeta.setBytes(mpf.getBytes());
				String temp_folder = systemConfig.getConfig(SystemConfig.TEMP_FOLDER);
				temp_folder = temp_folder.endsWith(System.getProperty("file.separator")) == true ? temp_folder
						: temp_folder + System.getProperty("file.separator");
				logger.debug("temp path: {}", temp_folder + fileMeta.getPhysicalFileName());
				// copy file to local disk (make sure the path
				// "e.g. D:/temp/files" exists)
				File f = new File(temp_folder + fileMeta.getPhysicalFileName());
				if (!f.exists())
					f.createNewFile();
				FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(f));

			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error(e + ":" + e.getMessage());
				e.printStackTrace();
			}
			// 2.4 add to files

		}
		response.setContentType("text/plain");
		response.setHeader("Content-Type", "text/plain");
		// result will be like this
		// [{"fileName":"app_engine-85x77.png","fileSize":"8 Kb","fileType":"image/png"},...]
		ObjectMapper mapper = new ObjectMapper();
		String result = "";
		try {
			result = mapper.writeValueAsString(fileMeta);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// return fileMeta;
		return result;
	}

	/***************************************************
	 * URL: /rest/controller/get/{value} get(): get file as an attachment
	 * 
	 * @param response
	 *            : passed by the server
	 * @param value
	 *            : value from the URL
	 * @return void
	 ****************************************************/
	@RequestMapping(value = "/get/{value}", method = RequestMethod.GET)
	public void get(HttpServletResponse response, @PathVariable String value) {
		FileMeta getFile = files.get(Integer.parseInt(value));
		try {
			response.setContentType(getFile.getFileType());
			response.setHeader("Content-disposition", "attachment; filename=\"" + getFile.getFileName() + "\"");
			FileCopyUtils.copy(getFile.getBytes(), response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Date: Jun 24, 2016 11:13:14 AM
	 * 
	 * @param request
	 * @param request2
	 * @param model
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/uploadMsgAttachment", method = RequestMethod.POST)
	public @ResponseBody Object uploadMsgAttachment(MultipartHttpServletRequest request, HttpServletRequest request2, Model model,
			HttpServletResponse response) {

		String source = "";
		ReturnObject returnObject = new ReturnObject();

		try {
			// 1. build an iterator
			Iterator<String> itr = request.getFileNames();
			while (itr.hasNext()) {
				// 2.1 get next MultipartFile
				MultipartFile mpf = request.getFile(itr.next());

				try {

					String contentType = mpf.getContentType();
					System.out.println("##contentType##" + contentType);

					BufferedImage bufferedImage = ImageIO.read(mpf.getInputStream());
					if (bufferedImage == null) {
						throw new Exception("Invalid image");
					}

				} catch (Exception e) {
					throw new Exception("Invalid image");
				}

				String extensao = "";
				if (mpf.getOriginalFilename().lastIndexOf('.') == -1) {
					/*
					 * file khong co extent
					 */
					source = "@@@" + mpf.getOriginalFilename().substring(0, mpf.getOriginalFilename().lastIndexOf('.')) + "__"
							+ (new Date()).getTime();
				} else {
					extensao = mpf.getOriginalFilename().substring(mpf.getOriginalFilename().lastIndexOf('.') + 1, mpf.getOriginalFilename().length())
							.toLowerCase();
					String newName = Utils.convertNewFileName(mpf.getOriginalFilename().substring(0, mpf.getOriginalFilename().lastIndexOf('.')));
					/*
					 * remove dau, ky tu dac biet
					 */
					source = "@@@" + newName + "__" + (new Date()).getTime() + "." + extensao;
				}

				String path = systemConfig.getConfig(SystemConfig.TEMP_FOLDER) + source;
				/*
				 * save vao folder temp
				 */
				try {
					FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(path));

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			returnObject.setStatus(ReturnObject.SUCCESS);
			returnObject.setRetObj(source);

		} catch (Exception e) {
			returnObject.setStatus(ReturnObject.ERROR);
			String msg = "<div class=\"alert alert-error fade in\"> <button class=\"close\" data-dismiss=\"alert\" type=\"button\">×</button>"
					+ e.getMessage() + "</div>";
			returnObject.setMessage(msg);
		}

		return returnObject;
	}

	@RequestMapping(value = "/uploadTemp", method = RequestMethod.POST)
	public @ResponseBody String uploadTemp(MultipartHttpServletRequest request, HttpServletRequest request2, Model model,
			HttpServletResponse response) {

		// 1. build an iterator
		Iterator<String> itr = request.getFileNames();
		MultipartFile mpf = null;
		String path;
		String source = "";
		while (itr.hasNext()) {
			// 2.1 get next MultipartFile

			mpf = request.getFile(itr.next());
			String extensao = "";
			if (mpf.getOriginalFilename().lastIndexOf('.') == -1) {
				/*
				 * file khong co extent
				 */
				source = "@@@" + mpf.getOriginalFilename().substring(0, mpf.getOriginalFilename().lastIndexOf('.')) + "__" + (new Date()).getTime();
			} else {
				extensao = mpf.getOriginalFilename().substring(mpf.getOriginalFilename().lastIndexOf('.') + 1, mpf.getOriginalFilename().length())
						.toLowerCase();
				String newName = Utils.convertNewFileName(mpf.getOriginalFilename().substring(0, mpf.getOriginalFilename().lastIndexOf('.')));
				/*
				 * remove dau, ky tu dac biet
				 */
				source = "@@@" + newName + "__" + (new Date()).getTime() + "." + extensao;
			}

			path = systemConfig.getConfig(SystemConfig.TEMP_FOLDER) + source;
			/*
			 * save vao folder temp
			 */
			try {
				FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(path));

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return source;
	}

	@RequestMapping(value = "/uploadVideo", method = RequestMethod.POST)
	public @ResponseBody List<String> uploadServlet(MultipartHttpServletRequest request, HttpServletRequest request2, Model model,
			HttpServletResponse response) {

		// 1. build an iterator
		Iterator<String> itr = request.getFileNames();
		MultipartFile mpf = null;
		List<String> filenameLst = new ArrayList<String>();
		String path;
		while (itr.hasNext()) {
			// 2.1 get next MultipartFile

			mpf = request.getFile(itr.next());
			String extensao = "";
			String source = "";
			if (mpf.getOriginalFilename().lastIndexOf('.') == -1) { // file
																	// khong co
																	// extent
				source = "@@@" + mpf.getOriginalFilename().substring(0, mpf.getOriginalFilename().lastIndexOf('.')) + "__" + (new Date()).getTime();
			} else {
				extensao = mpf.getOriginalFilename().substring(mpf.getOriginalFilename().lastIndexOf('.') + 1, mpf.getOriginalFilename().length())
						.toLowerCase();
				String newName = Utils.convertNewFileName(mpf.getOriginalFilename().substring(0, mpf.getOriginalFilename().lastIndexOf('.'))); // remove
																																				// dau,
																																				// ky
																																				// tu
																																				// dac
																																				// biet
				source = "@@@" + newName + "__" + (new Date()).getTime() + "." + extensao;
			}

			path = systemConfig.getConfig(SystemConfig.TEMP_FOLDER) + source; // save
																				// vao
			// temp
			try {
				FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(path));

				String target;
				if (extensao.equals("avi") || extensao.equals("wmv") || extensao.equals("wma")) { // convert
																									// to
																									// flv
					target = ConvertVideo.convert(systemConfig.getConfig(SystemConfig.TEMP_FOLDER), source);
				} else if (extensao.equals("pnj") || extensao.equals("jpg") || extensao.equals("jpeg")) { // compress
																											// image
					target = CompressJPEGFile.Process(systemConfig.getConfig(SystemConfig.TEMP_FOLDER), source);
				} else {
					target = source; // don't convert
				}
				filenameLst.add(target);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return filenameLst;
	}

	@RequestMapping(value = "/showVideo", method = RequestMethod.GET)
	@ResponseBody
	public String showVideo(Model model, HttpServletRequest request, Locale locale) throws Exception {
		return request.getParameter("fileName");
	}

	@RequestMapping(value = "/showVideo_PopupHomepage", method = RequestMethod.GET)
	public String showVideo_PopupHomepage(Model model, HttpServletRequest request, Locale locale) throws Exception {

		String fileName = request.getParameter("fileName");
		String language = request.getParameter("language");
		model.addAttribute("fileName", fileName);
		model.addAttribute("language", language);

		return "master_data.popup_homepage.video";
	}

	@RequestMapping(value = "/showImage", method = RequestMethod.GET)
	public String showImage(Model model, HttpServletRequest request, Locale locale, HttpServletResponse response) throws Exception {

		String flag = request.getParameter("flag");
		String listfilename = request.getParameter("listFile");
		String removeImage = request.getParameter("removeImage");
		String lstImageLinkShow = request.getParameter("lstImageLinkShow");
		List<String> list = new ArrayList<String>();
		List<String> lstLink = new ArrayList<String>();
		String[] arrayList = listfilename.split(",");

		String[] arrayListLink = lstImageLinkShow.split("@@@");
		for (int i = 0; i < arrayList.length; i++) {
			list.add(arrayList[i]);
			if (StringUtils.isNotEmpty(lstImageLinkShow)) {
				lstLink.add(arrayListLink[i]);
			}
		}

		model.addAttribute("listResult", list);
		model.addAttribute("lstLink", lstLink);
		model.addAttribute("flag", flag);
		model.addAttribute("removeImage", removeImage);
		response.setContentType("text/html; charset=UTF-8");
		return "master_data.banner.image.show";
	}

	@RequestMapping(value = "/showImageView", method = RequestMethod.GET)
	public String showImageView(Model model, HttpServletRequest request, Locale locale, HttpServletResponse response) throws Exception {

		String flag = request.getParameter("flag");
		String listfilename = request.getParameter("listFile");
		String lstImageLinkShow = request.getParameter("lstImageLinkShow");
		List<String> list = new ArrayList<String>();
		List<String> lstLink = new ArrayList<String>();
		String[] arrayList = listfilename.split(",");
		String[] arrayListLink = lstImageLinkShow.split("@@@");
		for (int i = 0; i < arrayList.length; i++) {
			list.add(arrayList[i]);
			if (StringUtils.isNotEmpty(lstImageLinkShow)) {
				lstLink.add(arrayListLink[i]);
			}
		}

		model.addAttribute("listResult", list);
		model.addAttribute("lstLink", lstLink);
		model.addAttribute("flag", flag);
		response.setContentType("text/html; charset=UTF-8");
		return "master_data.banner.image.show.view";
	}

	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void download(@RequestParam(required = true) String fileName, @RequestParam(required = false) String downloadViewID,
			HttpServletRequest request, HttpServletResponse response) {

		// String userAgent = request.getHeader("user-agent");
		if (StringUtils.isEmpty(fileName)) {
			return;
		}

		fileName = StringUtils.replace(fileName, "%2f", "\\");
		fileName = StringUtils.replace(fileName, "/", "\\");
		fileName = StringUtils.replace(fileName, "..\\", "");

		try {

			String path = "";
			File fileTempCheck = new File(systemConfig.getConfig(SystemConfig.REPOSITORY_ROOT_FOLDER) + fileName);

			if (!fileTempCheck.exists() && fileName.startsWith("@@@")) {
				path = systemConfig.getConfig(SystemConfig.TEMP_FOLDER) + fileName;
			} else {
				path = systemConfig.getConfig(SystemConfig.REPOSITORY_ROOT_FOLDER) + fileName;
			}

			File fileTemp = new File(path);
			FileInputStream fileInputStream = new FileInputStream(fileTemp);

			byte[] arr = new byte[1024];
			int numRead = -1;

			response.addHeader("Content-Length", Long.toString(fileTemp.length()));
			if (StringUtils.isNotEmpty(request.getParameter("isDownload")) && request.getParameter("isDownload").equalsIgnoreCase("download")) {
				response.setContentType("application/octet-stream");
			} else {
				response.setContentType(getContentType(fileName));
			}
			/*
			 * if (userAgent.toLowerCase().contains("chrome")) { response.addHeader("Content-Disposition",
			 * "inline; filename=\"" + fileTemp.getName() + "\""); } else { response.addHeader("Content-Disposition",
			 * "inline; filename=\"" + fileTemp.getName() + "\""); }
			 */
			response.addHeader("Content-Disposition", "inline; filename=\"" + fileTemp.getName() + "\"");
			while ((numRead = fileInputStream.read(arr)) != -1) {
				response.getOutputStream().write(arr, 0, numRead);
			}
			fileInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/downloadCKE", method = RequestMethod.GET)
	public void downloadCKE(@RequestParam(required = true) String fileName, @RequestParam(required = false) String downloadViewID,
			HttpServletRequest request, HttpServletResponse response) {
		String userAgent = request.getHeader("user-agent");
		if (StringUtils.isEmpty(fileName)) {
			return;
		}
		try {
			String domain = "";
			String path = "";
			File fileTempCheck = new File(domain + systemConfig.getConfig(SystemConfig.REPOSITORY_ROOT_FOLDER) + fileName);
			if (!fileTempCheck.exists() && fileName.startsWith("@@@")) {
				path = systemConfig.getConfig(SystemConfig.TEMP_FOLDER) + fileName;
			} else {
				path = systemConfig.getConfig(SystemConfig.REPOSITORY_ROOT_FOLDER) + "userfiles/" + fileName;
			}
			File fileTemp = new File(domain + path);

			FileInputStream fileInputStream = new FileInputStream(fileTemp);
			byte[] arr = new byte[1024];
			int numRead = -1;

			response.addHeader("Content-Length", Long.toString(fileTemp.length()));
			if (StringUtils.isNotEmpty(request.getParameter("isDownload")) && request.getParameter("isDownload").equalsIgnoreCase("download")) {
				response.setContentType("application/octet-stream");
			} else {
				response.setContentType(getContentType(fileName));
			}
			/*
			 * if (userAgent.toLowerCase().contains("chrome")) { response.addHeader("Content-Disposition",
			 * "inline; filename=\"" + fileTemp.getName() + "\""); } else { response.addHeader("Content-Disposition",
			 * "inline; filename=\"" + fileTemp.getName() + "\""); }
			 */
			response.addHeader("Content-Disposition", "inline; filename=\"" + fileTemp.getName() + "\"");
			while ((numRead = fileInputStream.read(arr)) != -1) {
				response.getOutputStream().write(arr, 0, numRead);
			}
			fileInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Autowired
	private ServletContext servletContext;

	@RequestMapping(value = "/preview", method = RequestMethod.GET)
	public void preview(@RequestParam(required = true) String fileName, @RequestParam(required = false) String downloadViewID,
			HttpServletRequest request, HttpServletResponse response) {
		String userAgent = request.getHeader("user-agent");
		try {
			File fileTemp = new File(servletContext.getRealPath("/WEB-INF/temp_folder") + System.getProperty("file.separator") + fileName);

			FileInputStream fileInputStream = new FileInputStream(fileTemp);
			byte[] arr = new byte[1024];
			int numRead = -1;

			response.addHeader("Content-Length", Long.toString(fileTemp.length()));
			response.setContentType("application/pdf");

			response.addHeader("Content-Disposition", "attachment; filename=\"" + fileTemp.getName() + "\"");
			while ((numRead = fileInputStream.read(arr)) != -1) {
				response.getOutputStream().write(arr, 0, numRead);
			}
			fileInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getContentType(String fileName) {
		String contentType = "";
		String extensao = "";
		if (fileName.lastIndexOf('.') != -1) { // file co extent
			extensao = fileName.substring(fileName.lastIndexOf('.') + 1, fileName.length()).toLowerCase();
		}

		if (extensao.equals("pdf"))
			contentType = "application/pdf";
		else if (extensao.equals("txt"))
			contentType = "text/plain";
		else if (extensao.equals("exe"))
			contentType = "application/octet-stream";
		else if (extensao.equals("zip"))
			contentType = "application/zip";
		else if (extensao.equals("doc"))
			contentType = "application/msword";
		else if (extensao.equals("xls"))
			contentType = "application/vnd.ms-excel";
		else if (extensao.equals("xlsx"))
			contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		else if (extensao.equals("ppt"))
			contentType = "application/vnd.ms-powerpoint";
		else if (extensao.equals("gif"))
			contentType = "image/gif";
		else if (extensao.equals("png"))
			contentType = "image/png";
		else if (extensao.equals("jpeg"))
			contentType = "image/jpeg";
		else if (extensao.equals("jpg"))
			contentType = "image/jpeg";
		else if (extensao.equals("mp3"))
			contentType = "audio/mpeg";
		else if (extensao.equals("wav"))
			contentType = "audio/x-wav";
		else if (extensao.equals("mpeg"))
			contentType = "video/mpeg";
		else if (extensao.equals("mpg"))
			contentType = "video/mpeg";
		else if (extensao.equals("mpe"))
			contentType = "video/mpeg";
		else if (extensao.equals("mov"))
			contentType = "video/quicktime";
		else if (extensao.equals("avi"))
			contentType = "video/x-msvideo";
		else if (extensao.equals("flv"))
			contentType = "video/flv";
		else
			contentType = "application/octet-stream";

		return contentType;
	}

}
