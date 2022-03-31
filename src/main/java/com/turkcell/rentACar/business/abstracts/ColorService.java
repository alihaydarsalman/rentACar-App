package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.dtos.get.GetColorDto;
import com.turkcell.rentACar.entities.dtos.list.ColorListDto;
import com.turkcell.rentACar.entities.requests.create.CreateColorRequest;
import com.turkcell.rentACar.entities.requests.update.UpdateColorRequest;
import java.util.List;

public interface ColorService {

    DataResult<List<ColorListDto>> getAll();

    Result add(CreateColorRequest createColorRequest) throws BusinessException;

    Result update(UpdateColorRequest updateColorRequest) throws BusinessException;

    Result delete(int colorId) throws BusinessException;

    DataResult<GetColorDto> getById(int colorId) throws BusinessException;

    void isExistsByColorId(int colorId) throws BusinessException;

}
