package net.devs404.alerta_conecta_backend.controller;

import net.devs404.alerta_conecta_backend.database.occurrence.OccurrenceManage;
import net.devs404.alerta_conecta_backend.database.occurrence.entity.Occurrence;
import net.devs404.alerta_conecta_backend.database.occurrence.microdata.OccurrenceLocation;
import net.devs404.alerta_conecta_backend.database.occurrence.microdata.OccurrencePriority;
import net.devs404.alerta_conecta_backend.database.occurrence.microdata.OccurrenceStatus;
import net.devs404.alerta_conecta_backend.database.occurrence.microdata.OccurrenceType;
import net.devs404.alerta_conecta_backend.database.resources.ResourcesManage;
import net.devs404.alerta_conecta_backend.util.TimestampPlus;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/database/occurrence")
public class OccurrenceDataController
{
    @GetMapping("/getall")
    public static List<Map<String, Object>> getAll()
    {
    	System.out.println("Requisitou a coleta geral!");
    	List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
    	
        List<Occurrence> list = OccurrenceManage.getAllOccurrences();
        for(Occurrence x : list)
        {
        	Map<String, Object> map = new HashMap<String, Object>();
        	map.put("id", x.getId());
        	map.put("title", x.getTitle());
        	map.put("date", x.getDate());
        	map.put("victims", x.getVictims());
        	map.put("details", x.getDetails());
        	map.put("status", x.getStatus());
        	map.put("priority", x.getPriority());
        	map.put("type", x.getType());
        	map.put("latitude", x.getLocation().getLatitude());
        	map.put("longitude", x.getLocation().getLongitude());
        	
        	mapList.add(map);
        }
        return mapList;
    }

    
    @PostMapping(value = "/registry", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public static ResponseEntity<String> registryOne(
    		@RequestPart("title") String title,
    		@RequestPart("data") String date,
    		@RequestPart("victims") String victims,
    		@RequestPart("details") String details,
    		@RequestPart("status") String status,
    		@RequestPart("priority") String priority,
    		
    		@RequestPart("latitude") String latitude,
    		@RequestPart("longitude") String longitude,
    		
    		@RequestPart("occurrencetype") String type,
    		
    		@RequestPart("images") MultipartFile[] images
    )
    {
    	OccurrenceStatus trueStatus = OccurrenceStatus.valueOf(status);
    	OccurrencePriority truePriority = OccurrencePriority.valueOf(priority);
    	Timestamp trueDate = Timestamp.from(Instant.parse(date));
    	
    	Occurrence newOccurrence = new Occurrence(title, trueDate, victims, details, trueStatus, truePriority);
    	newOccurrence.setLocation(new OccurrenceLocation(Double.valueOf(latitude), Double.valueOf(longitude)));
    	newOccurrence.setType(new OccurrenceType(Integer.valueOf(type)));
    	
    	String folder = ResourcesManage.saveResources(images);
        boolean registered = OccurrenceManage.registryOccurrence(newOccurrence, folder);
        if(registered)
        {
            System.out.println("Ocorrencia registrada com sucesso!");
            return ResponseEntity.status(HttpStatus.CREATED).body("sucesso ao cadastrar usuário!");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("falha ao cadastrar usuário!");
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getOccurrenceById(@PathVariable("id") long id) {
        try {
        	Occurrence occurrence = OccurrenceManage.getOccurrence(id);
        	List<Path> imgPaths = ResourcesManage.getResources(occurrence.getFolderName());
        	
            Map<String, Object> mapOccurrence = new HashMap<>();
            mapOccurrence.put("title", occurrence.getTitle());
            mapOccurrence.put("date", occurrence.getDate());
            mapOccurrence.put("victims", occurrence.getVictims());
            mapOccurrence.put("details", occurrence.getDetails());
            mapOccurrence.put("status", occurrence.getStatus());
            mapOccurrence.put("priority", occurrence.getPriority());
            mapOccurrence.put("latitude", occurrence.getLocation().getLatitude());
            mapOccurrence.put("longitude", occurrence.getLocation().getLongitude());
            mapOccurrence.put("occurrencetype", occurrence.getType().getId());
            mapOccurrence.put("images", Arrays.asList(imgPaths));
            
            System.out.println("Ocorrencia "+id+" resgatada com sucesso!");
            return ResponseEntity.ok(mapOccurrence);
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "Ocorrência não encontrada"));
        }
    }
    

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Map<String, Object>> partialUpdateOccurrence(
    		@PathVariable("id") long id,
    		@RequestPart("title") String title,
    		@RequestPart("victims") String victims,
    		@RequestPart("details") String details,
    		@RequestPart("status") String status,
    		@RequestPart("priority") String priority) 
    {
    	try {
            
            
            Occurrence occurrence = new Occurrence();
            occurrence.setTitle(title);
            occurrence.setVictims(victims);
            occurrence.setDetails(details);
            occurrence.setStatus(status);
            occurrence.setPriority(priority);

            boolean isUpdated = OccurrenceManage.updateOccurrence(id, occurrence);
            if (!isUpdated) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("error", "Ocorrência não encontrada"));
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Ocorrência atualizada parcialmente com sucesso");
            response.put("id", id);
            response.put("newTitle", title);
            response.put("newVictims", victims);
            response.put("newDetails", details);
            response.put("newStatus", status);
            response.put("newPriority", priority);
            
            System.out.println("Atualização de: " + title + " ID: "+ id +" feita com sucesso!");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Erro ao atualizar ocorrência: " + e.getMessage()));
        }
    }
    
    @DeleteMapping("/{id}")
    public static ResponseEntity<Map<String, Object>> archiveOne(@PathVariable("id") Long id)
    {
        boolean archived = OccurrenceManage.archiveOccurrence(id);
        if(archived)
        {
        	Map<String, Object> response = new HashMap<String, Object>();
        	response.put("success", true);
            response.put("message", "Ocorrência arquivada com sucesso");
            response.put("id", id);
        	
            System.out.println("Ocorrencia arquivada com sucesso!");
            return ResponseEntity.ok(response);
        }
        return null;
    }
}
