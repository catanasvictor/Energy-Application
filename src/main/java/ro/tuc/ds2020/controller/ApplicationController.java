package ro.tuc.ds2020.controller;

import ro.tuc.ds2020.service.ApplianceService;
import ro.tuc.ds2020.view.LoginPage;

public class ApplicationController {

    private LoginPage loginPage;
    private ApplianceService applianceService;

    public ApplicationController(LoginPage loginPage) {
        this.loginPage = loginPage;
    }

    public void setApplianceService(ApplianceService applianceService) {
        this.applianceService = applianceService;
        this.loginPage.setApplianceService(applianceService);
    }
}
