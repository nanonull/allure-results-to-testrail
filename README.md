    Allure version: 2.4.1
    TestRail version: v6.2.3.1114 (API v2)

Example script to update TestRail test results using generic allure-results: conversion7.PushToTestRailRun

Example of allure-2-testrail mapping can be found in: /src/main/resources/testrail/mapping.json
Mapping is done by fullName field from allure-result.json

It's supposed your test framework will create this file (implementation using test listeners and interceptors for example).

Example set of env vars:

    LOGIN=<user-name-in-test-rail>;PASSWORD=<password>;TESTRAIL_URL=https://sample.testrail/;PROJECT_ID=10;RUN_ID=832;ABSOLUTE_PATH_ALLURE_RESULTS=/Users/dmytro/projects/railq/src/main/resources/allure-results;ABSOLUTE_PATH_ALLURE_MAPPING=/Users/dmytro/projects/railq/src/main/resources/testrail/mapping.json
    
       where ABSOLUTE_PATH_ALLURE_MAPPING - is a generated mapping file   

