package com.sangle.selenium.data;

import com.sangle.selenium.utils.CsvHelper;
import com.sangle.selenium.utils.JsonHelper;
import java.util.List;
import java.util.Map;
import org.testng.annotations.DataProvider;

public final class TestDataProviders {

    private TestDataProviders() {
        throw new IllegalStateException("Utility class");
    }

    @DataProvider(name = "userDataFromJson")
    public static Object[][] userDataFromJson() {
        List<TestUser> users = JsonHelper.readListFromClasspath("test-data/users.json", TestUser.class);
        return DataProviderUtils.wrapSingleColumn(users);
    }

    @DataProvider(name = "userDataFromCsv")
    public static Object[][] userDataFromCsv() {
        List<Map<String, String>> rows = CsvHelper.readAsMapsFromResource("test-data/users.csv");
        return DataProviderUtils.wrapKeyValue(rows);
    }
}
