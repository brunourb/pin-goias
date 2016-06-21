package gov.goias.sistema.repositories;

import gov.goias.sistema.entidades.Aluno;

import javax.inject.Named;
import java.util.*;

@Named
public class AlunoRepository {

    private Map<Integer, Aluno> dbAluno;

    public AlunoRepository()
    {
        dbAluno = new HashMap<>();

        for (Integer i=0; i<20; i++)
        {
            Aluno aluno = new Aluno(i, "Aluno-" + i, new Date(), "1",  true);
            dbAluno.put(i, aluno);
        }
    }

    public Optional<Aluno> findById(Integer id)
    {
        return Optional.ofNullable(dbAluno.get(id));
    }

    public Optional<Aluno> save(Aluno aluno)
    {
        dbAluno.put(aluno.getId(), aluno);
        return Optional.of(aluno);
    }

    public Optional<Aluno> update(Aluno aluno)
    {
        dbAluno.put(aluno.getId(), aluno);
        return Optional.of(aluno);
    }

    public void delete(Aluno aluno)
    {
        dbAluno.remove(aluno.getId());
    }

    public Integer obtemQuantidadeTotalRegistros()
    {
        return dbAluno.size();
    }

    public Optional<List<Aluno>> listarAlunos(Integer offset, Integer limit)
    {
        List<Aluno> lista = new ArrayList<>(dbAluno.values());
        offset = Optional.ofNullable(offset).orElseGet(() -> new Integer(0));
        limit = Optional.ofNullable(limit).orElseGet(() -> lista.size());

        return Optional.of(lista.subList(offset, offset + limit));
    }
}
