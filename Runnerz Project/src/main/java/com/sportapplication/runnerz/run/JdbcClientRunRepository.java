package com.sportapplication.runnerz.run;



import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static org.springframework.web.servlet.function.RequestPredicates.param;

@Repository

public class JdbcClientRunRepository {

private static final Logger log = Logger.getLogger(JdbcClientRunRepository.class.getName());
  private final JdbcClient jdbcClient;

    public JdbcClientRunRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Run> findAll(){
        return jdbcClient.sql("select * from run")
                .query(Run.class)
                .list();
    }

    public Optional<Run> findById(Integer id){
        return jdbcClient.sql("SELECT id,title,started_on,completed_on,miles,location FROM Run WHERE id=:id")
                .param(id)
                .query(Run.class)
                .optional();
    }
    public void create(Run run){
        var update=jdbcClient.sql("insert into Run (id,title,started_on,completed_on,miles,location) values (?,?,?,?,?,?)")
                .params(List.of(run.id(),run.title(),run.startedOn(),run.completedOn(),run.miles(),run.location().toString()))
                .update();

        Assert.state(update == 1, "Failed to update run"+run.title());
    }
    public void update(Run run, Integer id){
        var update=jdbcClient.sql("update run set title=?, started_on=?, completed_on=?, miles=?, location=? where id=?")
                .param(List.of(run.title(),run.startedOn(),run.completedOn(),run.miles(),run.location().toString(),id))
                .update();
        Assert.state(update == 1, "Failed to update run"+run.title());
    }
    public void delete(Integer id){
        var update=jdbcClient.sql("delete from Run where id=:id")
                .param(id)
                .update();
        Assert.state(update == 1, "Failed to delete run"+id);
    }

    public int count(){
        return jdbcClient.sql("select * from run")
                .query().listOfRows().size();

    }
    public void saveAll(List<Run> runs){
        runs.forEach(run -> create(run));
    }



}
