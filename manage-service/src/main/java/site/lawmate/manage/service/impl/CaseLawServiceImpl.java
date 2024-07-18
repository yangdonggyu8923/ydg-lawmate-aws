package site.lawmate.manage.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.lawmate.manage.domain.dto.CaseLawDetailDto;
import site.lawmate.manage.domain.dto.CaseLawDto;
import site.lawmate.manage.domain.dto.SearchCriteria;
import site.lawmate.manage.repository.CaseLawRepository;
import site.lawmate.manage.service.CaseLawService;
import site.lawmate.user.domain.dto.UserDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CaseLawServiceImpl implements CaseLawService {
    private final CaseLawRepository caselawRepository;


    @Override
    public List<CaseLawDto> getCaseLawList() {
        return caselawRepository.getCaseLawList();
    }

    @Override
    public CaseLawDetailDto getCaseLawDetail(String serialNumber) {
        return caselawRepository.getCaseLawDetail(serialNumber);
    }

    @Override
    public List<CaseLawDto> getCaseLawListByKeyword(SearchCriteria criteria) {
        return caselawRepository.getCaseLawListByKeyword(criteria);
    }


}
