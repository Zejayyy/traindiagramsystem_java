package com.itsymion.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itsymion.controller.DataForms.TrackData;
import com.itsymion.domain.Track;

public interface ITrackService extends IService<Track>
{

    boolean modify(TrackData trackData, Integer lineId, Integer trackId);
}
