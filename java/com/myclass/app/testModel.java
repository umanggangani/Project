package com.myclass.app;

public class testModel {
    private String testId;
    private String testName;
    private String testDes;
    private String testFile;
    private String testDate;
    private String testSTime;
    private String testETime;
    private String testMark;
    private String testGetMark;
    private String testNum;
    private String testAns;

    public testModel(String testId, String testName, String testDes, String testFile,String testAns,String testMark,String testGetMark ,String testNum,String testDate, String testSTime, String testETime) {
        this.testId = testId;
        this.testName = testName;
        this.testDes = testDes;
        this.testFile = testFile;
        this.testDate = testDate;
        this.testSTime = testSTime;
        this.testETime = testETime;
        this.testMark = testMark;
        this.testNum = testNum;
        this.testAns = testAns;
        this.testGetMark =testGetMark;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestDes() {
        return testDes;
    }

    public void setTestDes(String testDes) {
        this.testDes = testDes;
    }

    public String getTestFile() {
        return testFile;
    }

    public void setTestFile(String testFile) {
        this.testFile = testFile;
    }

    public String getTestDate() {
        return testDate;
    }

    public void setTestDate(String testDate) {
        this.testDate = testDate;
    }

    public String getTestSTime() {
        return testSTime;
    }

    public void setTestSTime(String testSTime) {
        this.testSTime = testSTime;
    }

    public String getTestETime() {
        return testETime;
    }

    public void setTestETime(String testETime) {
        this.testETime = testETime;
    }

    public String getTestMark() {
        return testMark;
    }

    public void setTestMark(String testMark) {
        this.testMark = testMark;
    }

    public String getTestNum() {
        return testNum;
    }

    public void setTestNum(String testNum) {
        this.testNum = testNum;
    }

    public String getTestAns() {
        return testAns;
    }

    public void setTestAns(String testAns) {
        this.testAns = testAns;
    }

    public String getTestGetMark() {
        return testGetMark;
    }

    public void setTestGetMark(String testGetMark) {
        this.testGetMark = testGetMark;
    }
}
