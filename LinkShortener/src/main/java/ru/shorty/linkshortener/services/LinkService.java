package ru.shorty.linkshortener.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shorty.linkshortener.dto.LinkCreateDto;
import ru.shorty.linkshortener.dto.LinkUpdateDto;
import ru.shorty.linkshortener.dto.LinkViewDto;
import ru.shorty.linkshortener.exceptions.LinkDoesNotExistsException;
import ru.shorty.linkshortener.exceptions.InnerRefAlreadyExistsException;
import ru.shorty.linkshortener.exceptions.ExternalRefDoesNotExistsException;
import ru.shorty.linkshortener.models.LinkModel;
import ru.shorty.linkshortener.models.UserModel;
import ru.shorty.linkshortener.repositories.LinkRepository;
import ru.shorty.linkshortener.repositories.UserRepository;

import java.util.*;

@Service
@Transactional
public class LinkService {

    //region Properties && constructor

    private final ModelMapper modelMapper;

    private final LinkRepository linkRepository;

    private final UserRepository userRepository;

    public LinkService(LinkRepository linkRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.linkRepository = linkRepository;
        this.userRepository = userRepository;
    }

    //endregion

    //region Rest methods

    public Map<String, List<LinkViewDto>>  getAllDtoCast(UUID userUid) {
        List<LinkViewDto> dtosList = linkRepository.findAllByUserUidOrderByCreateDtDesc(userUid).stream()
            .map(this::convertLinkModelToViewDto).toList();
        return Collections.singletonMap("data", dtosList);
    }

    public LinkViewDto getByUid(UUID userUid, UUID linkUid) {
        return linkRepository.findByUidAndUserUid(linkUid, userUid).map(this::convertLinkModelToViewDto)
            .orElseThrow(LinkDoesNotExistsException::new);
    }

    public void deleteByUid(UUID userUid, UUID linkUid) {
        if (!linkRepository.existsByUidAndUserUid(linkUid, userUid))
            throw new LinkDoesNotExistsException();
        linkRepository.deleteByUidAndUserUid(linkUid, userUid);
    }

    public Map<String, String> getExternalRefByInner(String innerRef) {
        LinkModel model = linkRepository.findFirstByInnerRef(innerRef).orElseThrow(ExternalRefDoesNotExistsException::new);
        LinkViewDto dtoModel = convertLinkModelToViewDto(model);
        return Collections.singletonMap("externalRef", dtoModel.getExternalRef());
    }

    public void createLink(UUID userUid, LinkCreateDto dto) {
        if (linkRepository.existsByInnerRef(dto.getInnerRef()))
            throw new InnerRefAlreadyExistsException();

        LinkModel model = convertLinkCreateDtoToModel(dto);

        Optional<UserModel> userOptional = userRepository.findByUid(userUid);
        UserModel user = userOptional.orElseThrow(() -> new RuntimeException("User not found"));
        model.setUser(user);
        linkRepository.save(model);
    }

    public void updateLink(UUID userUid, UUID linkUid, LinkUpdateDto dto) {
        LinkModel model = linkRepository.findByUidAndUserUid(userUid, linkUid).orElseThrow(LinkDoesNotExistsException::new);
        model.setTitle(dto.getTitle());
        linkRepository.save(model);
    }

    //endregion

    //region Other

    public LinkModel convertLinkCreateDtoToModel(LinkCreateDto dto) {
        return modelMapper.map(dto, LinkModel.class);
    }

    public LinkViewDto convertLinkModelToViewDto(LinkModel model) {
        return modelMapper.map(model, LinkViewDto.class);
    }

    //endregion
}
