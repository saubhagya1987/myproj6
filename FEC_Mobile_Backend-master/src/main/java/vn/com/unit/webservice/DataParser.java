package vn.com.unit.webservice;

import java.lang.reflect.Type;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import vn.com.unit.fe_credit.entity.ContractApi;
import vn.com.unit.fe_credit.entity.ContractDetailApi;
import vn.com.unit.fe_credit.entity.ContractInstallmentApi;
import vn.com.unit.fe_credit.entity.PaymentHistoryAPI;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class DataParser {
	public static List<ContractApi> parseToContractApiList(JSONArray jsonArray) {
		Type listType = new TypeToken<List<ContractApi>>() {
		}.getType();
		Gson gson = new GsonBuilder().setDateFormat("yyy-MM-dd'T'HH:mm:ss")
				.create();
		List<ContractApi> list = gson.fromJson(jsonArray.toString(), listType);
		return list;
	}
	
	public static List<ContractDetailApi> parseToContractDetailApiList(JSONArray jsonArray) {
		Type listType = new TypeToken<List<ContractDetailApi>>() {
		}.getType();
		Gson gson = new GsonBuilder().setDateFormat("yyy-MM-dd'T'HH:mm:ss")
				.create();
		List<ContractDetailApi> list = gson.fromJson(jsonArray.toString(), listType);
		return list;
	}
	
	
	public static ContractApi parseToContractApi(JSONObject jsonObject) {
		Type customerType = new TypeToken<ContractApi>() {
		}.getType();
		Gson gson = new GsonBuilder().setDateFormat("yyy-MM-dd'T'HH:mm:ss")
				.create();
		ContractApi contractApi = gson.fromJson(jsonObject.toString(), customerType);
		return contractApi;
	}
	
	public static List<PaymentHistoryAPI> parseToPaymentHistoryList(JSONArray jsonArray) {
		Type listType = new TypeToken<List<PaymentHistoryAPI>>() {
		}.getType();
		Gson gson = new GsonBuilder().setDateFormat("yyy-MM-dd'T'HH:mm:ss")
				.create();
		List<PaymentHistoryAPI> list = gson.fromJson(jsonArray.toString(), listType);
		return list;
	}
	
	public static List<ContractInstallmentApi> parseToContractInstallmentList(JSONArray jsonArray) {
		Type listType = new TypeToken<List<ContractInstallmentApi>>() {
		}.getType();
		Gson gson = new GsonBuilder().setDateFormat("yyy-MM-dd'T'HH:mm:ss")
				.create();
		List<ContractInstallmentApi> list = gson.fromJson(jsonArray.toString(), listType);
		return list;
	}
}
