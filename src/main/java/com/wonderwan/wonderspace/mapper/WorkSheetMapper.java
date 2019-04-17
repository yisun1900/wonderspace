package com.wonderwan.wonderspace.mapper;

import com.wonderwan.wonderspace.model.entity.WorkSheet;
import com.wonderwan.wonderspace.model.param.WorkSheetVO;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkSheetMapper {

    WorkSheet queryWorkSheet(WorkSheetVO workSheetVO);
}
