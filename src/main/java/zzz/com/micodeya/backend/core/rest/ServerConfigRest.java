package zzz.com.micodeya.backend.core.rest;

import java.io.File;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Enumeration;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javax.swing.filechooser.FileSystemView;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import lombok.extern.slf4j.Slf4j;
import zzz.com.micodeya.backend.core.util.JeBoot;

@Slf4j
@RestController
@RequestMapping("/server")
public class ServerConfigRest {

  @Value("${server.servlet.context-path}")
  private String servletContextPath;
  

  @GetMapping("/status")
  public String getServerStatus() {
    
    String pomSpringbootVersion = "no econtrado";
    String pomAppVersion = "no econtrado";
    String pomProjectName = "no econtrado";

    String springbootVersion = "no econtrado";
    String appVersion = "no econtrado";
    String projectName = "no econtrado";

    File pomFile = new File("pom.xml");
    if (pomFile.exists()) {
      try {

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(pomFile);

        doc.getDocumentElement().normalize();

        Element springbootVersionElement = (Element) doc.getElementsByTagName("version").item(0);

        pomSpringbootVersion = springbootVersionElement.getTextContent();

        Element nameElement = (Element) doc.getElementsByTagName("name").item(0);
        pomProjectName = nameElement.getTextContent();

        Element appVersionElement = (Element) doc.getElementsByTagName("version").item(1);
        pomAppVersion = appVersionElement.getTextContent();

      } catch (Exception e) {
        e.printStackTrace();
        log.warn("No se pudo obtener información del archivo pom.xml");
      }
    }

    try {

      InputStream is = ServerConfigRest.class.getResourceAsStream("/META-INF/MANIFEST.MF");
      Manifest manifest = new Manifest(is);
      Attributes attributes = manifest.getMainAttributes();

      springbootVersion = attributes.getValue("Spring-Boot-Version");
      projectName = attributes.getValue("Implementation-Title");
      appVersion = attributes.getValue("Implementation-Version");

    } catch (Exception e) {
      e.printStackTrace();
      log.warn("No se pudo obtener información del archivo MANIFEST.MF");
    }

    // return stats;
    String retorno = "\nINFORMACIÓN DEL SERVIDOR\n";
    int mb = 1024 * 1024;
    retorno += "\nEstoy up =) " + JeBoot.getFechaStringHHMMSS(new Date());

    retorno += "\n";
    retorno += "\nContext Path: " + servletContextPath;

    retorno += "\nProject Name: " + projectName;
    retorno += "\nApplication Version: " + appVersion;
    retorno += "\nSpringboot Version: " + springbootVersion;
    retorno += "\npom.xml Project Name: " + pomProjectName;
    retorno += "\npom.xml Application Version: " + pomAppVersion;
    retorno += "\npom.xml Springboot Version: " + pomSpringbootVersion;



    retorno += "\n";
    try {
      Runtime runtime = Runtime.getRuntime();
      retorno += ("\nTomcat Memoria Utilizada: " + (runtime.totalMemory() - runtime.freeMemory()) / mb + " mb");
      retorno += ("\nTomcat Memoria Libre: " + runtime.freeMemory() / mb + " mb");
      retorno += ("\nTomcat Memoria Total: " + runtime.totalMemory() / mb + " mb");
      retorno += ("\nTomcat Memoria Maxima: " + runtime.maxMemory() / mb + " mb");
    } catch (Exception e) {
      log.error("Error Runtime.getRuntime()", e);
      retorno += ("\nError Runtime.getRuntime(): " + e.getMessage());
    }

    retorno += "\n";

    try {

      long memorySize = ((com.sun.management.OperatingSystemMXBean) ManagementFactory
          .getOperatingSystemMXBean()).getTotalPhysicalMemorySize();

      retorno += "\nMemoria RAM: " + (memorySize / mb) + " mb";

    } catch (Exception e) {
      log.error("Error Memoria RAM", e);
      retorno += ("\nError Memoria RAM: " + e.getMessage());
    }

    retorno += "\n";

    try {

      

      FileSystemView fsv = FileSystemView.getFileSystemView();
      File[] drives = File.listRoots();
      retorno += "\nAlmacenamiento:";
      if (drives != null && drives.length > 0) {
        for (File aDrive : drives) {
          String letra = "";
          String tipo = "";
          String espacioTotal = "";
          String espacioLibre = "";

          if (aDrive != null)
            letra = "-----Punto de Montaje: " + aDrive;
          if (fsv.getSystemTypeDescription(aDrive) != null)
            tipo = "\nTipo: " + fsv.getSystemTypeDescription(aDrive);

          espacioTotal = " Espacio total: " + aDrive.getTotalSpace() / mb + " mb\n";
          espacioLibre = " Espacio libre: " + aDrive.getFreeSpace() / mb + " mb\n";

          retorno += ("\n" + letra + tipo + espacioTotal + espacioLibre + "\n");

        }
      }

    } catch (Exception e) {
      log.error("Error almacenamiento", e);
      retorno += ("\nError almacenamiento: " + e.getMessage());
    }

    // retorno += "Redes: " + getNICcards();
    retorno += "\n";

    return "<pre>" + retorno + "</pre>";
  }

  public static String getNICcards() {
    String retorno = "";

    try {
      InetAddress localhost = InetAddress.getLocalHost();
      retorno += ("\n IP Addr: " + localhost.getHostAddress());
      // Just in case this host has multiple IP addresses....
      InetAddress[] allMyIps = InetAddress.getAllByName(localhost.getCanonicalHostName());
      if (allMyIps != null && allMyIps.length > 1) {
        retorno += ("\n Full list of IP addresses:");
        for (int i = 0; i < allMyIps.length; i++) {

          retorno += ("\n    " + allMyIps[i]);
        }
      }
    } catch (UnknownHostException e) {
      retorno += ("\n (error retrieving server host name)");
    }

    try {
      retorno += ("\nFull list of Network Interfaces:");
      for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
        NetworkInterface intf = en.nextElement();
        retorno += ("\n    " + intf.getName() + " " + intf.getDisplayName());
        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
          retorno += ("\n        " + enumIpAddr.nextElement().toString());
        }
      }
    } catch (SocketException e) {
      retorno += ("\n (error retrieving network interface list)");
    }

    return retorno;

  }
}
