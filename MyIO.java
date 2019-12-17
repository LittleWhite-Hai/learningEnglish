package userinterface;

import java.io.*;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.Date;

public class MyIO {
	private String[][] wordDataPackage = new String[2][1000];
	private GregorianCalendar today = new GregorianCalendar();
	private int year = today.get(Calendar.YEAR);
	private int month = today.get(Calendar.MONTH) + 1;
	private int day = today.get(Calendar.DAY_OF_MONTH);
	private static MyIO i = null;
	private int allWordsIndex = 0;

	public String[] getWords() {
		return wordDataPackage[0];
	}

	public String[] getNames() {
		return wordDataPackage[1];
	}

	public static MyIO getMyIOInstance() throws IOException {

		if (i == null)
			i = new MyIO();
		return i;
	}

	public static void finishWork(String[] modifiedWords, String[] wordsName) throws IOException {
		int modifiedWordsIndex = 0;
		while (modifiedWords[modifiedWordsIndex] != null) {
			File f = new File(wordsName[modifiedWordsIndex]);
			f.createNewFile();
			BufferedWriter s = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "UTF-8"));
			s.write(modifiedWords[modifiedWordsIndex]);
			s.close();
			modifiedWordsIndex++;
		}

	}

	private MyIO() throws IOException {

		fillWordsToArray();
	}

	private void fillWordsToArray() throws IOException {
		if (allWordsIndex == 0) {
			File todayFileName = new File(combineInToString(year, month, day));
			wordDataPackage[1][allWordsIndex] = combineInToString(year, month, day);
			if (todayFileName.isFile())
				wordDataPackage[0][allWordsIndex] = "1";
			else
				wordDataPackage[0][allWordsIndex] = "0";
			allWordsIndex++;
		}

		if (today.compareTo(new GregorianCalendar(2017, 6, 0)) < 1)
			return;
		today.add(Calendar.DAY_OF_MONTH, -1);
		String fileName = combineInToString(today.get(Calendar.YEAR), today.get(Calendar.MONTH) + 1,
				today.get(Calendar.DAY_OF_MONTH));
		File fileWord = new File(fileName);
		if (fileWord.isFile()) {

			BufferedReader f = new BufferedReader(new InputStreamReader(new FileInputStream(fileWord), "UTF-8"));
			char[] c = new char[1000];
			f.read(c);
			f.close();
			if (new String(c).indexOf(":", 0) != -1) {
				String cc = new String(c);
				cc = cc.substring(0, cc.indexOf("*") + 1);
				wordDataPackage[0][allWordsIndex] = cc;
				wordDataPackage[1][allWordsIndex] = fileName;
				allWordsIndex++;
			}
		}
		fillWordsToArray();

	}


	private static String combineInToString(int t0, int t1, int t2) {

		String s1;
		String s2;
		t0 = t0 % 100;
		s1 = "" + t1;
		if (t1 < 10)
			s1 = "0" + t1;
		s2 = "" + t2;
		if (t2 < 10)
			s2 = "0" + t2;
		return "C:/English/" + t0 + "_" + s1 + "_" + s2 + ".txt";

	}

}
