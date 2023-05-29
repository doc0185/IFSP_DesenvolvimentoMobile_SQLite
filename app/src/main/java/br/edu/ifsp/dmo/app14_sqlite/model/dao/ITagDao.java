package br.edu.ifsp.dmo.app14_sqlite.model.dao;

import java.util.List;

import br.edu.ifsp.dmo.app14_sqlite.model.entities.Tag;

public interface ITagDao{

    void create(Tag tag);

    boolean delete(Tag tag);

    Tag find(String tagName);

    List<Tag> findAll();
}
