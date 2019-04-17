package com.wonderwan.wonderspace.service;

import com.wonderwan.wonderspace.mapper.WorkSheetMapper;
import com.wonderwan.wonderspace.model.entity.WorkSheet;
import com.wonderwan.wonderspace.model.param.WorkSheetVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkSheetService {

    @Autowired
    private WorkSheetMapper workSheetMapper;

    public WorkSheet queryWorkSheet(WorkSheetVO workSheetVO) {
        return workSheetMapper.queryWorkSheet(workSheetVO);
    }
}
