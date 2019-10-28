package com.self;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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

			// Ignore First Line
			br.readLine();

			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] row = line.split(firstLevelSplitBy);
				System.out.println(row);

				JSONObject obj = null;
				JSONObject section = null;
				for (int i = 0; i < sectionArray.size(); i++) {
					obj = (JSONObject) sectionArray.get(i);
					if (row[0].equals(obj.get("section"))) {
						section = obj;
					}
				}

				if (null == section) {
					section = new JSONObject();
					section.put("section", row[0]);
					JSONArray questions = new JSONArray();
					section.put("questions", questions);

					// Add New Section into Array.
					sectionArray.add(section);
				}

				String[] qas = row[1].split(cvsSplitBy);
				JSONObject question = new JSONObject();
				question.put("type", "dropdown");
				question.put("qid", qas[0]);
				question.put("question", qas[1]);
				question.put("answer", "0");
				String typeOfField = qas[2];
				question.put("type", typeOfField);
				question.put("weight", qas[3]);
				question.put("majorVersion", qas[4]);
				question.put("minorVersion", qas[5]);

				JSONArray options = new JSONArray();
				JSONObject option = null;

				if(!"radio".equals(typeOfField)){
					option = new JSONObject();
					option.put("text", "Select");
					option.put("score", "0");
					options.add(option);
				}

				for (int i = 6; i < qas.length;) {
					option = new JSONObject();
					option.put("text", qas[i++]);
					option.put("score", qas[i++]);
					options.add(option);
				}
				question.put("options", options);

				((JSONArray) section.get("questions")).add(question);

				JSONArray categories = new JSONArray();
				question.put("categories", categories);

				for (int i = 2; i < row.length; i++) {
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

	public static void main(String[] args) throws IOException {

		// String filePath =
		// "/home/administrator/eclipse-workspace/cass_app/src/main/java/test.csv";

		if (null == args || args.length < 1 || "".equals(args[0].trim())) {
			System.out.println("Pass File Path as Argument..");
		} else {

			String assessmentJson = new CSVToAssessment().convertCsvToJson(args[0]);

			if (args.length > 1) {
				FileWriter fileWriter = new FileWriter(args[1]+".json");
				fileWriter.write(assessmentJson);
				fileWriter.flush();
				fileWriter.close();
			} else {
				System.out.println(assessmentJson);
			}
		}
	}

}
