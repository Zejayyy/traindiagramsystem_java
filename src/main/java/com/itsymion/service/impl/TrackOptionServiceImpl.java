package com.itsymion.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itsymion.dao.ITrackOptionDao;
import com.itsymion.domain.TrackOption;
import com.itsymion.service.ITrackOptionService;
import org.springframework.stereotype.Service;

@Service
public class TrackOptionServiceImpl extends ServiceImpl<ITrackOptionDao, TrackOption> implements ITrackOptionService
{
}
