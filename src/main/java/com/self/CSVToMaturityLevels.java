package com.self;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;

public class CSVToMaturityLevels {

	public String convertCsvToJson(String filePath) {

		String maturityLevelStr = "";
		File f = new File(filePath);
		BufferedReader br = null;
		String line = "";
		String firstLevelSplitBy = ",,";
		String cvsSplitBy = ",";

		try {

			br = new BufferedReader(new FileReader(f));

			JSONObject maturityLevel = new JSONObject();
			JSONArray maturityLevelArray = new JSONArray();
			maturityLevel.put("maturityLevels", maturityLevelArray);

			// Ignore First Line
			br.readLine();

			while ((line = br.readLine()) != null)  {
				String[] mls = line.split(cvsSplitBy);
				JSONObject maturity = new JSONObject();
				maturity.put("title", mls[1]);
				maturity.put("minimum", mls[2]);
				maturity.put("maximum", mls[3]);

				maturityLevelArray.add(maturity);
			}
			maturityLevelStr = maturityLevel.toJSONString();
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
		return maturityLevelStr;
	}

	public static void main(String[] args) throws IOException {

		// String filePath =
		// "/home/administrator/eclipse-workspace/cass_app/src/main/java/test.csv";

		if (null == args || args.length < 1 || "".equals(args[0].trim())) {
			System.out.println("Pass File Path as Argument..");
		} else {

			String maturityLevelStr = new CSVToMaturityLevels().convertCsvToJson(args[0]);

			if (args.length > 1) {
				FileWriter fileWriter = new FileWriter(args[1]+".json");
				fileWriter.write(maturityLevelStr);
				fileWriter.flush();
				fileWriter.close();
			} else {
				System.out.println(maturityLevelStr);
			}
		}
	}

}
