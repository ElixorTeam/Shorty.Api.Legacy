package ru.shorty.linkshortener.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shorty.linkshortener.dto.common.LinkDto;
import ru.shorty.linkshortener.exceptions.common.ExternalRefIsNotValidException;
import ru.shorty.linkshortener.exceptions.common.InnerRefAlreadyExistsException;
import ru.shorty.linkshortener.exceptions.common.LinkDoesNotExistsException;
import ru.shorty.linkshortener.models.LinkModel;
import ru.shorty.linkshortener.models.UserModel;
import ru.shorty.linkshortener.properties.AppProperties;
import ru.shorty.linkshortener.repositories.LinkRepository;
import ru.shorty.linkshortener.repositories.UserRepository;
import ru.shorty.linkshortener.utils.UnsortedUtil;

import java.util.*;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
@RequiredArgsConstructor
@Transactional
public class LinkService {

    //region Properties && constructor

    ModelMapper modelMapper;

    LinkRepository linkRepository;

    UserRepository userRepository;

    AppProperties appProperties;

    //endregion

    //region Rest methods

    public Map<String, List<LinkDto>>  getAllDtoCast(UUID userUid) {
        List<LinkDto> dtosList = linkRepository.findAllByUserUidOrderByCreateDtDesc(userUid).stream()
            .map(this::convertLinkModelToLinkDto).toList();
        return Collections.singletonMap("data", dtosList);
    }

    public LinkDto getByUid(UUID userUid, UUID linkUid) {
        return linkRepository.findByUidAndUserUid(linkUid, userUid).map(this::convertLinkModelToLinkDto)
            .orElseThrow(LinkDoesNotExistsException::new);
    }

    public void deleteByUid(UUID userUid, UUID linkUid) {
        if (!linkRepository.existsByUidAndUserUid(linkUid, userUid))
            throw new LinkDoesNotExistsException();
        linkRepository.deleteByUidAndUserUid(linkUid, userUid);
    }

    public void createLink(UUID userUid, LinkDto dto) {
        if (linkRepository.existsByInnerRef(dto.getInnerRef()))
            throw new InnerRefAlreadyExistsException();

        if (!UnsortedUtil.getRegexStatusOfUrl(dto.getExternalRef()))
            throw new ExternalRefIsNotValidException();

        if (dto.getExternalRef().contains(appProperties.getFrontRedirectUrl()))
            throw new ExternalRefIsNotValidException();

        Optional<UserModel> userOptional = userRepository.findByUid(userUid);
        UserModel user = userOptional.orElseThrow(() -> new RuntimeException("User not found"));

        LinkModel model = convertLinkDtoToModel(dto);
        model.setUser(user);

        linkRepository.save(model);
    }

    public void updateLink(UUID userUid, UUID linkUid, LinkDto dto) {
        LinkModel model = linkRepository.findByUidAndUserUid(linkUid, userUid).orElseThrow(LinkDoesNotExistsException::new);
        model.setTitle(dto.getTitle());
        linkRepository.save(model);
    }

    //endregion

    //region Other

    public LinkModel convertLinkDtoToModel(LinkDto dto) {
        return modelMapper.map(dto, LinkModel.class);
    }

    public LinkDto convertLinkModelToLinkDto(LinkModel model) {
        return modelMapper.map(model, LinkDto.class);
    }

    //endregion
}
