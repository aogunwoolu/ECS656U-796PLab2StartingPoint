package com.example.grpc.client.grpcclient;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.Arrays;

@Controller 
public class PingPongEndpoint { 
    //distributed-cw-a
    
    // definition of the 2 matrices to be used in the multiplication + addition
    Integer[][] currentA;
    Integer[][] currentB;

    // ips of the servers
    String[] ips = new String[]{"localhost","localhost","localhost","localhost","localhost","localhost"};//{"34.136.68.107", "34.68.69.77", "35.239.210.63", "35.238.58.44"};

    // clientservice definiton
	GRPCClientService grpcClientService;  

    // constructor
	@Autowired
    public PingPongEndpoint(GRPCClientService grpcClientService) {
        grpcClientService.setup(ips);
        this.grpcClientService = grpcClientService;
    }    


    // web page mappings (GET)
	@GetMapping("/ping")
    public String ping(Model model) {
        model.addAttribute("response", grpcClientService.ping());
        return "test";
    }

    @GetMapping("/")
	public String upload() {
        return "upload";
	}

    @GetMapping("/add")
	public String add(Model model) {
		model.addAttribute("C", grpcClientService.add(currentA, currentB));
        return "result";
	}

    @GetMapping("/blockadd")
	public String blockadd(Model model) {
		model.addAttribute("C", grpcClientService.add(currentA, currentB));
        return "result";
	}
    
    @GetMapping("/blockmult")
	public String blockmult(Model model) {
		model.addAttribute("C", grpcClientService.blockMult(currentA, currentB));
        return "result";
	}

    @GetMapping("/mult")
	public String mult(Model model) {
		model.addAttribute("C", grpcClientService.mult(currentA, currentB));
        return "result";
	}

    @GetMapping("/display")
	public String displayUploaded(Model model) {
        model.addAttribute("A", currentA);
        model.addAttribute("B", currentB);
		//return grpcClientService.add();
        return "display";
	}

    // POST mapping for uploading the matrices
	@PostMapping("/upload")
    public RedirectView handleFileUpload(@RequestParam("matrixA") MultipartFile matrixA, @RequestParam("matrixB") MultipartFile matrixB, RedirectAttributes redirectAttributes)
    throws Exception {

        // check if the files are empty
        String contentA = new String(); 
        String contentB = new String(); 
        try {
            contentA = new String(matrixA.getBytes());
            contentB = new String(matrixB.getBytes());

			System.out.println(contentA);
			System.out.println(contentB);
        } catch (IOException e) {
            System.out.println("Can't read file input stream");
        }

        // splitting rows (based on new line)
        String[] split_contentA = contentA.split("\n");
        String[] split_contentB = contentB.split("\n");
        int length = split_contentA.length;

        Integer[][] inputA = new Integer[length][length];
        Integer[][] inputB = new Integer[length][length];

        for (int i=0; i < length; i++) {
            split_contentA[i] = split_contentA[i].trim();
            split_contentB[i] = split_contentB[i].trim();

            // splitting cols (based on space)
            String[] single_intA = split_contentA[i].split(",");
            String[] single_intB = split_contentB[i].split(",");

            for (int j=0; j < length; j++) {
                inputA[i][j] = Integer.valueOf(single_intA[j]);
                inputB[i][j] = Integer.valueOf(single_intB[j]);
            }
        }

        if (length < 4) {
            throw new Exception("matrix must be a power of 2: ");
			//System.out.println("too small");
        }
        else if (inputA.length != inputB.length) {
            //throw new InputMatricesNotSameSizeException();
            //throw new RuntimeException("This was thrown intentionally");
            System.out.println("not the same size");
        }

        //int[][] intResult = new int[length][length];
        String returnResult = new String(); 

        //check if power of 2
        if (length == (int)Math.pow(2, Math.floor(Math.log(length)/Math.log(2)))) {
            currentA = inputA;
            currentB = inputB;
        }
        else {
            throw new Exception("matrix must be a power of 2: ");
			//System.out.println("not power of 2");
        }

        RedirectView rv = new RedirectView();
        rv.setUrl("display");
        return rv;
    }
}
