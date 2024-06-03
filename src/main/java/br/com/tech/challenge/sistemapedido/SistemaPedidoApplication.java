package br.com.tech.challenge.sistemapedido;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableRabbit
@EnableFeignClients
@SpringBootApplication
@EnableTransactionManagement
public class SistemaPedidoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaPedidoApplication.class, args);
	}

}
