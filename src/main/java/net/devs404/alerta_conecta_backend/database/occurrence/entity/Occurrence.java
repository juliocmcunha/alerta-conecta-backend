package net.devs404.alerta_conecta_backend.database.occurrence.entity;

import net.devs404.alerta_conecta_backend.database.occurrence.microdata.OccurrenceAddress;
import net.devs404.alerta_conecta_backend.database.occurrence.microdata.OccurrenceLocation;
import net.devs404.alerta_conecta_backend.database.occurrence.microdata.OccurrencePriority;
import net.devs404.alerta_conecta_backend.database.occurrence.microdata.OccurrenceStatus;
import net.devs404.alerta_conecta_backend.database.occurrence.microdata.OccurrenceType;

import java.sql.Timestamp;
import java.util.Date;

public class Occurrence
{
    private long id;


    private String titulo;

    private Timestamp data;
    
    private String envolvidos;
    private String detalhe;
    private String nomePasta;

    private OccurrenceStatus status;
    private OccurrencePriority prioridade;

    private OccurrenceType tipo;
    private OccurrenceLocation local;

    
    public Occurrence(String title, String victims, String details, String priority)
    {
    	this.titulo = title;
    	this.envolvidos = victims;
    	this.detalhe = details;
    	
    	Enum.valueOf(OccurrencePriority.class, priority);
    	
    	this.status = OccurrenceStatus.Em_andamento;
    }
    
    public Occurrence(String title, Timestamp date, String victims, String details, OccurrencePriority priority)
    {
    	this.titulo = title;
    	this.data = date;
    	this.envolvidos = victims;
    	this.detalhe = details;
    	this.prioridade = priority;
    	
    	this.status = OccurrenceStatus.Em_andamento;
    }
    
    public Occurrence(long id, String title, Timestamp date, String victims, String details, OccurrencePriority priority)
    {
    	this.id = id;
    	this.titulo = title;
    	this.data = date;
    	this.envolvidos = victims;
    	this.detalhe = details;
    	this.prioridade = priority;
    	
    	this.status = OccurrenceStatus.Em_andamento;
    }

    public void promoteStatus(OccurrenceStatus newStatus)
    {
        status = newStatus;
    }

    public void promotePriority(OccurrencePriority newPriority)
    {
        prioridade = newPriority;
    }

    /// GETTERS&SETTERS
    public long getId() {return id;}
    public String getTitle() {return titulo;}
    public Timestamp getDate(){return data;}
    public String getVictims(){return envolvidos;}
    public String getDetails(){return detalhe;}
    public String getFolderName() {return nomePasta;}

    public OccurrenceStatus getStatus(){return status;}
    public OccurrencePriority getPriority(){return prioridade;}

    public OccurrenceType getType(){return tipo;}
    public OccurrenceLocation getLocation() {return local;}
    

    public void setFolderName(String newName){nomePasta = newName;}
    
    public void setType(OccurrenceType newType){tipo = newType;}
    public void setLocation(OccurrenceLocation newLocation) {local = newLocation;} 
    
    public void setPriority(String newPriority){prioridade = Enum.valueOf(OccurrencePriority.class, newPriority);}
    public void setStatus(String newStatus){status = Enum.valueOf(OccurrenceStatus.class, newStatus);}
    
    
    ///DEPRECATED
    @Deprecated
    private OccurrenceAddress endereco;
    @Deprecated
	public OccurrenceAddress getAddress(){return endereco;}
    @Deprecated
    public void setAddress(OccurrenceAddress newAddress){endereco=newAddress;}
}
