package org.sklsft.generator.repository.backup.file.impl;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.sklsft.generator.model.exception.InvalidFileException;
import org.sklsft.generator.persistence.backup.file.impl.SemiColonExcelCsvFileParserImpl;
import org.sklsft.generator.persistence.backup.file.model.TextDelimitedFile;

public class ExcelCsvFileParserImplTest {
	
	@Test
	public void readCsv() throws InvalidFileException, IOException {
		SemiColonExcelCsvFileParserImpl parser = new SemiColonExcelCsvFileParserImpl();
		TextDelimitedFile content = parser.readData("src/test/resources/csv/test.csv");
		List<Object[]> data = content.getData();
		Assert.assertEquals(data.size(),2);
		Assert.assertEquals(data.get(0)[0],"test,test");
		Assert.assertEquals(data.get(0)[1],"test1");
		Assert.assertEquals(data.get(0)[2],"test2");
		Assert.assertEquals(data.get(1)[0],"test;test");
		Assert.assertEquals(data.get(1)[1],"test3");
		Assert.assertEquals(data.get(1)[2],"test4");
	}

}
