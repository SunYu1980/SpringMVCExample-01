package com.example.servingwebcontent.controller;

import java.util.List;

import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.servingwebcontent.entity.CountryEntity;
import com.example.servingwebcontent.entity.Customer;
import com.example.servingwebcontent.form.CountryForm;
import com.example.servingwebcontent.repository.CountryEntityMapper;
import com.google.gson.Gson;

@Controller
public class CountryNewController {

	@Autowired
	private CountryEntityMapper mapper;
	
	@GetMapping("/countryInit")
	public String init(CountryForm countryForm) {
		
		return "country";
	}
	
	@GetMapping("/countryUpdate")
	public String countryShow(CountryForm countryForm) {		
		return "countryUpdate";
	}
	
	@PostMapping("/countryAdd")
	public String countryNew(@Validated CountryForm countryForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
            return "country";
        }

		CountryEntity countryEntity = new CountryEntity();
		BeanUtils.copyProperties(countryForm, countryEntity);
		mapper.insert(countryEntity);
		return "countryShow";
	}
	
	@PostMapping("/update")
	@ResponseBody
	public String update(@Validated CountryForm countryForm) {

		CountryEntity countryEntity = new CountryEntity();
		BeanUtils.copyProperties(countryForm, countryEntity);
		mapper.updateByPrimaryKey(countryEntity);
		
		return "更新正常终了";
	}
	
	@GetMapping("/getCountrys")
	@ResponseBody
	public String getCountrys() {

		Gson gson = new Gson(); 
		/**
		 * List of country retrieved from the database.
		 */
		List<CountryEntity> list = mapper.select(SelectDSLCompleter.allRows());
		
		// 将list以json格式返回
		return gson.toJson(list);
	}
		
}
