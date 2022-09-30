package ro.tuc.ds2020.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.remoting.support.RemoteExporter;
import ro.tuc.ds2020.services.DeviceService;
import ro.tuc.ds2020.services.RecordService;
import ro.tuc.ds2020.services.UserService;

@Configuration
public class HessianConfig {

    private final RecordService recordService;
    private final UserService userService;
    private final DeviceService deviceService;

    public HessianConfig(RecordService recordService, UserService userService, DeviceService deviceService) {
        this.recordService = recordService;
        this.userService = userService;
        this.deviceService = deviceService;
    }

    @Bean(name = "/hellohessian")
    RemoteExporter sayHelloServiceHessian() {
        HessianServiceExporter exporter = new HessianServiceExporter();
        exporter.setService(new ApplianceServiceImpl(recordService, deviceService, userService));
        exporter.setServiceInterface(ApplianceService.class);
        return exporter;
    }
}
