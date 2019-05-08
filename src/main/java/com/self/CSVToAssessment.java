package com.self;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class CSVToAssessment {
	
	public String convertCsvToJson(String filePath) {

		String assessmentStr = "";
		File f = new File(filePath);
		BufferedReader br = null;
		String line = "";
		String firstLevelSplitBy = ",,";
		String cvsSplitBy = ",";

		try {

			br = new BufferedReader(new FileReader(f));

			JSONObject assessment = new JSONObject();
			JSONArray sectionArray = new JSONArray();
			assessment.put("sections", sectionArray);

			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] row = line.split(firstLevelSplitBy);

				JSONObject section = new JSONObject();
				JSONArray questions = new JSONArray();
				section.put("section", row[0]);
				section.put("questions", questions);

				sectionArray.add(section);

				String[] qas = row[1].split(cvsSplitBy);
				JSONObject question = new JSONObject();
				question.put("question", qas[0]);
				question.put("answer", "0");
				question.put("weight", qas[1]);

				JSONArray options = new JSONArray();
				JSONObject option = new JSONObject();
				option.put("text", "Select");
				option.put("score", "0");
				options.add(option);
				for (int i = 2; i < qas.length;) {
					option = new JSONObject();
					option.put("text", qas[i++]);
					option.put("score", qas[i++]);
					options.add(option);
				}
				question.put("options", options);
				questions.add(question);

				JSONArray categories = new JSONArray();
				question.put("categories", categories);
				
				for (int i = 2; i < row.length;i++) {
					String[] cs = row[i].split(cvsSplitBy);
					JSONObject category = new JSONObject();
					category.put("category", cs[0]);
					
					JSONArray subCategories = new JSONArray();
					category.put("subCategories", subCategories);
					
					JSONObject subCategory = null;
					for (int j = 1; j < cs.length; j++) {
						subCategory = new JSONObject();
						subCategory.put("subCategory", cs[j]);
						subCategories.add(subCategory);
					}
					categories.add(category);
				}
				
			}
			assessmentStr = assessment.toJSONString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return assessmentStr;
	}

	public static void main(String[] args) {
		String filePath = "/home/administrator/eclipse-workspace/cass_app/src/main/java/test.csv";
		System.out.println(new CSVToAssessment().convertCsvToJson(filePath));
	}

}
