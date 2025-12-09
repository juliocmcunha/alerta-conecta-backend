package net.devs404.alerta_conecta_backend;

import net.devs404.alerta_conecta_backend.GUI.GUIMain;
import net.devs404.alerta_conecta_backend.database.resources.ResourcesManage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application
{
    public static void main(String[] args)
    {
        //new GUIMain();
    	ResourcesManage.initResourcesService();
        SpringApplication.run(net.devs404.alerta_conecta_backend.Application.class, args);
    }
}