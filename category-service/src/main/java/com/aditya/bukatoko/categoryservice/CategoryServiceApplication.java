package com.aditya.bukatoko.categoryservice;

import io.ap4k.kubernetes.annotation.ImagePullPolicy;
import io.ap4k.kubernetes.annotation.KubernetesApplication;
import io.ap4k.kubernetes.annotation.Probe;
import io.ap4k.kubernetes.annotation.ServiceType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@KubernetesApplication(
		livenessProbe = @Probe(httpActionPath = "/actuator/health"),
		readinessProbe = @Probe(httpActionPath = "/actuator/health"),
		serviceType = ServiceType.LoadBalancer,
		imagePullPolicy = ImagePullPolicy.Always,
		group = "adityars"
)
public class CategoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CategoryServiceApplication.class, args);
	}

}
