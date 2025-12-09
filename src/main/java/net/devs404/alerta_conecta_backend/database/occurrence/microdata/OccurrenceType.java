package net.devs404.alerta_conecta_backend.database.occurrence.microdata;

public class OccurrenceType
{
    private long id;

    private String nome;
    private String descricao;

    public OccurrenceType(long id, String name, String desc)
    {
        this.id = id;
        this.nome = name;
        this.descricao = desc;
    }

    public OccurrenceType(long id, String name)
    {
        this.id = id;
        this.nome = name;
    }
    
    public OccurrenceType(long id)
    {
    	this.id = id;
    }

    public OccurrenceType(){}

    /// GETTERS&SETTERS
    public long getId() {return id;}
    public String getName(){return nome;}
    public String getDescription(){return descricao;}


    public void setId(long newId){id=newId;}
    public void setName(String newName){nome=newName;}
    public void setDescription(String newDesc){descricao=newDesc;}
}
