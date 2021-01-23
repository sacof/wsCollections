package com.collections.app.controller;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.collections.app.service.CallRestService;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;


@Controller
public class CollectionsController {

	public CollectionsController() {
	}
	
	@GetMapping("/list")
	public @ResponseBody JSONArray list( Model model){
		
		JSONArray collectionList = CallRestService.callRestService();
		if(collectionList != null)
		{
			return collectionList;
		}
		else
		{
			JSONObject json = new JSONObject();
			json.appendField("error", "Not found any collection");
			JSONArray notFound = new JSONArray();
			notFound.add(json);
			return notFound;
		}
	}

	private String getValue(LinkedHashMap<String, Object> colJson, String keyName)
	{
		String value = (String) colJson.getOrDefault(keyName,"");
		return value==null? "" : value;
	}
	
	
	@GetMapping(value = "/list/filter/{filter}")
	public @ResponseBody JSONArray ver(@PathVariable(value = "filter") String filter, Map<String, Object> model) {
		
		JSONArray collectionList = CallRestService.callRestService();
		if(collectionList != null)
		{
			if(filter != null && !filter.isEmpty())
			{
				Iterator<Object> colIterator = collectionList.iterator();
				JSONArray filterList = new JSONArray();
				while(colIterator.hasNext())
				{
					LinkedHashMap<String, Object> colJson = (LinkedHashMap<String, Object>) colIterator.next();
					String id = getValue(colJson, "id");
					String title = getValue(colJson, "title"); 
					String description = getValue(colJson, "description"); 
					LinkedHashMap<String, Object> coverPhoto = (LinkedHashMap<String, Object>)colJson.getOrDefault("cover_photo","");
					String coverPhotoId ="";
					if(coverPhoto != null && !coverPhoto.isEmpty())
					{
						coverPhotoId = getValue(coverPhoto, "id"); 
					}
					if(id.contains(filter)
						|| title.contains(filter)
						|| description.contains(filter)
						|| coverPhotoId.contains(filter))
					{
						filterList.add(colJson);
					}
					
				}
				if(filterList.isEmpty())
				{
					JSONObject json = new JSONObject();
					json.appendField("info", "No collection pass the filter");
					filterList.add(json);
				}
				return filterList;
			}
			else return collectionList;
		}
		else
		{
			JSONObject json = new JSONObject();
			json.appendField("error", "Not found any collection");
			JSONArray notFound = new JSONArray();
			notFound.add(json);
			return notFound;
		}
	}
}
