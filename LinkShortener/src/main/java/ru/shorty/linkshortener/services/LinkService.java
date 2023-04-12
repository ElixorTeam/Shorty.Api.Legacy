package ru.shorty.linkshortener.services;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.shorty.linkshortener.dto.LinkDto;
import ru.shorty.linkshortener.models.LinkModel;
import ru.shorty.linkshortener.exceptions.LinkDtoNullException;
import ru.shorty.linkshortener.exceptions.LinkTitleAlreadyExistsException;
import ru.shorty.linkshortener.exceptions.LinkDoesNotExistsException;
import ru.shorty.linkshortener.repositories.LinkRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
public class LinkService {

    //region Properties && constructor

    private final ModelMapper modelMapper;

    private final LinkRepository linkRepository;

    public LinkService(LinkRepository linkRepository, ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.linkRepository = linkRepository;
    }

    //endregion

    //region Rest methods

    public List<LinkModel> getAll() {
        return linkRepository.findAll(Sort.by(Sort.Direction.DESC, "createDt"));
    }

    public List<LinkDto> getAllDtoCast() {
        List<LinkDto> dtos = new ArrayList<>();
        for (LinkModel model: getAll())
            dtos.add(convertLinkModelToDto(model));
        return dtos;
    }

    public LinkDto getByUid(UUID link_uid) {
        LinkModel model = linkRepository.findByUid(link_uid).orElseThrow(LinkDoesNotExistsException::new);
        return convertLinkModelToDto(model);
    }

    public void deleteByUid(UUID link_uid) {
        if (!linkRepository.existsByUid(link_uid))
            throw new LinkDoesNotExistsException();
        linkRepository.deleteByUid(link_uid);
    }

    public void createLink(LinkDto dto) {
        if (dto.getRef() == null || dto.getTitle() == null)
            throw new LinkDtoNullException();
        if (linkRepository.existsByTitle(dto.getTitle()))
            throw new LinkTitleAlreadyExistsException();
        LinkModel model = convertLinkDtoToModel(dto);
        linkRepository.save(model);
    }

    public void updateLink(UUID link_uid, LinkDto dto) {
        LinkModel model = linkRepository.findByUid(link_uid).orElseThrow(LinkDoesNotExistsException::new);
        if (!Objects.equals(model.getTitle(), dto.getTitle())
                && linkRepository.existsByTitle(dto.getTitle()))
            throw new LinkTitleAlreadyExistsException();
        model.setRef(dto.getRef());
        model.setTitle(dto.getTitle());
        model.setActive(dto.isActive());
        linkRepository.save(model);
    }

    //endregion

    //region Other

    public LinkModel convertLinkDtoToModel(LinkDto dto) {
        return modelMapper.map(dto, LinkModel.class);
    }

    public LinkDto convertLinkModelToDto(LinkModel model) {
        return modelMapper.map(model, LinkDto.class);
    }

    //endregion
}
