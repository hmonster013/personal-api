package com.de013.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.de013.model.Paging;
import com.de013.model.Rest;
import com.de013.utils.JCode;
import com.de013.utils.JConstants;
import com.de013.utils.Utils;
import com.de013.utils.JCode.CommonCode;
import com.de013.utils.JCode.UserCode;

@RestController
public class BaseController {
	private static Logger log = LoggerFactory.getLogger(BaseController.class);

	@Autowired
	private MessageSource msg;
	public BaseController() {

	}

	public String msg(String code) {
		return msg.getMessage(code, null, new Locale("vi")); 
	}

	public String msg(String code, Object param) {
		return msg.getMessage(code, new String[] { param.toString() }, new Locale("vi"));
	}

	public String msg(String code, Object[] params) {
		return msg.getMessage(code, params, new Locale("vi"));
	}

	public ResponseEntity responseData(List<?> responseList, Page result) throws Exception {
		Map<String, Object> map = Utils.responseMap(responseList, result);
		return response(map);
	}

	/**
	 * Return REST with List object
	 * 
	 * @param responseList
	 * @param result
	 */

	public ResponseEntity responseList(List<?> responseList) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(JConstants.DATA_LIST, responseList);
		Paging paging = new Paging();
		paging.setTotalRows(responseList.size());
		map.put(Paging.class.getSimpleName().toLowerCase(), paging);
		return response(map);
	}

	public ResponseEntity responseList(List<?> responseList, Page result) {
		Map<String, Object> map = Utils.responseMap(responseList, result);
		return response(map);
	}

	protected ResponseEntity response() {
		return response(JCode.SUCCESS, msg(JCode.SUCCESS));
	}

	protected ResponseEntity success() {
		return response(JCode.SUCCESS, msg(JCode.SUCCESS));
	}

	protected ResponseEntity responseMessage(String message) {
		return response(JCode.SUCCESS, message);
	}

	protected ResponseEntity response(Object data) {
		if (data instanceof String) {
			return response(JCode.SUCCESS, data.toString());
		} else if (data instanceof CommonCode) {
			String code = CommonCode.valueOf(data.toString()).code;
			return response(code, msg(code));
		} else if (data instanceof UserCode) {
			String code = UserCode.valueOf(data.toString()).code;
			return response(code, msg(code));
		} else {
			return response(JCode.SUCCESS, data);
		}
	}

	protected ResponseEntity response(String status, String message) {
		log.debug("ResponseEntity: [" + status + "] [" + message + "]");
		Rest rest = new Rest(status, message);
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity(rest, headers, HttpStatus.OK);
	}

	protected ResponseEntity response(String status, Object data) {
		log.debug("ResponseEntity: " + status);
		Rest rest = new Rest(status, "OK");
		rest.setData(data);
		HttpHeaders headers = new HttpHeaders();

		return new ResponseEntity(rest, headers, HttpStatus.OK);
	}

	protected ResponseEntity response(String status, String meassage, Object data) {
		log.debug("ResponseEntity: " + status + " [" + status + "]");
		Rest rest = new Rest(status, meassage);
		rest.setData(data);
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity(rest, headers, HttpStatus.OK);
	}

}
