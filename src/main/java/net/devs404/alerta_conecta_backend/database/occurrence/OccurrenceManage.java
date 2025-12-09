package net.devs404.alerta_conecta_backend.database.occurrence;

import net.devs404.alerta_conecta_backend.database.DataBase;
import net.devs404.alerta_conecta_backend.database.occurrence.entity.Occurrence;
import net.devs404.alerta_conecta_backend.database.occurrence.microdata.OccurrenceLocation;
import net.devs404.alerta_conecta_backend.database.occurrence.microdata.OccurrencePriority;
import net.devs404.alerta_conecta_backend.database.occurrence.microdata.OccurrenceStatus;
import net.devs404.alerta_conecta_backend.database.occurrence.microdata.OccurrenceType;
import net.devs404.alerta_conecta_backend.util.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OccurrenceManage
{
    public static List<Occurrence> getAllOccurrences()
    {
        List<Occurrence> list = new ArrayList<>();
        String sql = """
                SELECT\s
                id_ocorrencia,
                titulo,
                data_hora,
                envolvidos,
                detalhes,
                status_atual,
                prioridade,
                t.id_tipo_ocorrencia as id_tipo,
                nome_tipo,
                descricao_tipo,
                e.latitude,
                e.longitude\s
                FROM\s
                ocorrencia o\s 
                JOIN tipo_ocorrencia t ON o.id_tipo_ocorrencia = t.id_tipo_ocorrencia\s
                JOIN endereco_ocorrencia e ON o.id_endereco_ocorrencia = e.id_endereco_ocorrencia;""";

        try {
            Connection connect = DataBase.connect();
            PreparedStatement ps = connect.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next())
            {
            	
                Occurrence newOccurrence = new Occurrence();
                newOccurrence.setId(rs.getInt("id_ocorrencia"));
                
                newOccurrence.setTitle(rs.getString("titulo"));
                newOccurrence.setTimestamp(rs.getTimestamp("data_hora"));
                newOccurrence.setVictims(rs.getString("envolvidos"));
                newOccurrence.setDetails(rs.getString("detalhes"));

                newOccurrence.promoteStatus(Enum.valueOf(OccurrenceStatus.class, rs.getString("status_atual")));
                newOccurrence.promotePriority(Enum.valueOf(OccurrencePriority.class, rs.getString("prioridade")));

                newOccurrence.setType(new OccurrenceType(rs.getInt("id_tipo"), rs.getString("nome_tipo")));
                newOccurrence.setLocation(new OccurrenceLocation(rs.getDouble("latitude"), rs.getDouble("longitude")));

                if(newOccurrence.getStatus() == OccurrenceStatus.Cancelada) {continue;}
                list.add(newOccurrence);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static Occurrence getOccurrence(long id)
    {
    	Occurrence occurrence = null;
    	String sql = """
                SELECT\s
                titulo,
                data_hora,
                envolvidos,
                detalhes,
                status_atual,
                prioridade,
                t.id_tipo_ocorrencia as id_tipo,
                t.nome_tipo,
                e.latitude,
                e.longitude\s
                FROM\s
                ocorrencia o 
                JOIN tipo_ocorrencia t ON o.id_tipo_ocorrencia = t.id_tipo_ocorrencia\s
    			JOIN endereco_ocorrencia e ON o.id_endereco_ocorrencia = e.id_endereco_ocorrencia;
                """;
    	try
    	{
    		Connection connect = DataBase.connect();
    		PreparedStatement ps = connect.prepareStatement(sql);
    		ResultSet rs = ps.executeQuery();
    		
    		while(rs.next())
    		{
    			Occurrence newOccurrence = new Occurrence();
    			newOccurrence.setTitle(rs.getString("titulo"));
                newOccurrence.setTimestamp(rs.getTimestamp("data_hora"));
                newOccurrence.setVictims(rs.getString("envolvidos"));
                newOccurrence.setDetails(rs.getString("detalhes"));

                newOccurrence.promoteStatus(Enum.valueOf(OccurrenceStatus.class, rs.getString("status")));
                newOccurrence.promotePriority(Enum.valueOf(OccurrencePriority.class, rs.getString("prioridade")));

                newOccurrence.setType(new OccurrenceType(rs.getInt("id_tipo"), rs.getString("nome_tipo")));
                
                newOccurrence.setLocation(new OccurrenceLocation(rs.getDouble("latitude"), rs.getDouble("longitude")));
    		}
    	} catch (SQLException e) {
            throw new RuntimeException(e);
        }
    	return occurrence;
    }
    
    public static boolean registryOccurrence(Occurrence newOccurrence, String resource)
    {
        OccurrenceLocation adr = newOccurrence.getLocation();
        String sql = "CALL sp_registrar_ocorrencia(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            Connection connect = DataBase.connect();
            PreparedStatement ps = connect.prepareStatement(sql);

            ps.setString(1, newOccurrence.getTitle());
            ps.setTimestamp(2, newOccurrence.getDate());
            ps.setString(3, newOccurrence.getVictims());
            ps.setString(4, newOccurrence.getDetails());
            ps.setString(5, newOccurrence.getStatus().name());
            ps.setString(6, StringUtils.capitalize(newOccurrence.getPriority().name()));
            ps.setString(7, resource);
            ps.setLong(8, newOccurrence.getType().getId());
            ps.setDouble(9, adr.getLatitude());
            ps.setDouble(10, adr.getLongitude());
            ps.execute();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static boolean updateOccurrence(long id, Occurrence updOccurrence)
    {
        String sql = String.format("UPDATE ocorrencia SET titulo = '%s', envolvidos = '%s', detalhes = '%s', status_atual = '%s', prioridade = '%s' WHERE id_ocorrencia = %s;",
                updOccurrence.getTitle(), updOccurrence.getVictims(), updOccurrence.getDetails(), updOccurrence.getStatus().name(), updOccurrence.getPriority().name(), id);

        try {
            Connection connect = DataBase.connect();
            PreparedStatement ps = connect.prepareStatement(sql);
            ps.execute();

            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean archiveOccurrence(long id)
    {
        String sql = String.format("UPDATE ocorrencia SET status_atual = 'Cancelada' WHERE id_ocorrencia = %s", id);
        try {
            Connection connect = DataBase.connect();
            PreparedStatement ps = connect.prepareStatement(sql);
            ps.execute();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
