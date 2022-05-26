package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.abstracts.ColorService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.ColorDao;
import com.turkcell.rentACar.entities.dtos.get.GetColorDto;
import com.turkcell.rentACar.entities.dtos.list.ColorListDto;
import com.turkcell.rentACar.entities.requests.create.CreateColorRequest;
import com.turkcell.rentACar.entities.requests.update.UpdateColorRequest;
import com.turkcell.rentACar.entities.sourceEntities.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.PushBuilder;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ColorManager implements ColorService {

    private final ModelMapperService modelMapperService;
    private final ColorDao colorDao;
    private final CarService carService;

    @Autowired
    public ColorManager(ModelMapperService modelMapperService, ColorDao colorDao, CarService carService) {
        this.modelMapperService = modelMapperService;
        this.colorDao = colorDao;
        this.carService = carService;
    }

    @Override
    public DataResult<List<ColorListDto>> getAll() {

        List<Color> colors = this.colorDao.findAll();

        List<ColorListDto> result = colors.stream()
                .map(color -> this.modelMapperService.forDto().map(color,ColorListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result,BusinessMessages.SUCCESS_LIST);
    }

    @Override
    public Result add(CreateColorRequest createColorRequest) throws BusinessException {

        isExistsByColorName(createColorRequest.getColorName());

        Color color = this.modelMapperService.forRequest().map(createColorRequest,Color.class);
        color.setColorId(0);
        this.colorDao.save(color);

        return new SuccessResult(BusinessMessages.SUCCESS_ADD);
    }

    @Override
    public Result update(UpdateColorRequest updateColorRequest) throws BusinessException {

        isExistsByColorId(updateColorRequest.getColorId());
        isExistsByColorName(updateColorRequest.getColorName());

        Color color = this.modelMapperService.forRequest().map(updateColorRequest,Color.class);
        this.colorDao.save(color);

        return new SuccessResult(BusinessMessages.SUCCESS_UPDATE);
    }

    @Override
    public Result delete(int colorId) throws BusinessException {

        isExistsByColorId(colorId);
        this.carService.isExistsColorByColorId(colorId);

        this.colorDao.deleteById(colorId);

        return new SuccessResult(BusinessMessages.SUCCESS_DELETE);
    }

    @Override
    public DataResult<GetColorDto> getById(int colorId) throws BusinessException {

        isExistsByColorId(colorId);

        Color color = this.colorDao.getById(colorId);
        GetColorDto getColorDto = this.modelMapperService.forDto().map(color,GetColorDto.class);

        return new SuccessDataResult<>(getColorDto,BusinessMessages.SUCCESS_GET);
    }

    private void isExistsByColorName(String colorName) throws BusinessException {
        if(this.colorDao.existsByColorName(colorName)){
            throw new BusinessException(BusinessMessages.ERROR_COLOR_NAME_EXISTS);
        }
    }

    @Override
    public void isExistsByColorId(int colorId) throws BusinessException {
        if(!this.colorDao.existsByColorId(colorId)){
            throw new BusinessException(BusinessMessages.ERROR_COLOR_NOT_FOUND);
        }
    }

    @Override
    public Color getColorById(int colorId) throws BusinessException {

        isExistsByColorId(colorId);

        return this.colorDao.findByColorId(colorId);
    }
}
