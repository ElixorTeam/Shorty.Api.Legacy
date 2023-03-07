package ru.shorty.linkshortener.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shorty.linkshortener.model.LinkModel;
import ru.shorty.linkshortener.repository.LinkRepository;

import java.util.List;
import java.util.UUID;

@Service
public class LinkService {

    @Autowired
    private LinkRepository linkRepository;

    public List<LinkModel> listAll() {
        return linkRepository.findAll();
    }

    public LinkModel get(UUID uid){
        return linkRepository.findById(uid).get();
    }

    public void save(LinkModel link){
        linkRepository.save(link);
    }

    public void delete(UUID uid) {
        linkRepository.deleteById(uid);
    }

}
