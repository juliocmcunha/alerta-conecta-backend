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

    public Occurrence(){}
    
    public Occurrence(String title, Timestamp date, String victims, String details, OccurrenceStatus status, OccurrencePriority priority)
    {
    	this.titulo = title;
    	this.data = date;
    	this.envolvidos = victims;
    	this.detalhe = details;
    	this.status = status;
    	this.prioridade = priority;
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
    

    public void setId(long newId){id = newId;}
    public void setTitle(String newTitle){titulo = newTitle;}
    public void setTimestamp(Timestamp newDate){data = newDate;}
    public void setVictims(String newListVict){envolvidos = newListVict;}
    public void setDetails(String newDetails){detalhe = newDetails;}
    public void setFolderName(String newFolderName){nomePasta = newFolderName;}

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
