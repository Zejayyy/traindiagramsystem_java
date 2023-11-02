package com.itsymion.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itsymion.controller.DataForms.TrackData;
import com.itsymion.controller.utils.R;
import com.itsymion.dao.ITrackDao;
import com.itsymion.domain.Track;
import com.itsymion.service.ITrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrackServiceImpl extends ServiceImpl<ITrackDao, Track> implements ITrackService
{
    @Autowired
    private ITrackDao trackDao;

    @Override
    public boolean modify(TrackData trackData, Integer lineId, Integer trackId)
    {

        int success = 0;

        for (int i = 0; i < trackData.getPlanList().size(); i++)
        {
            QueryWrapper<Track> queryWrapper = new QueryWrapper<>();
            queryWrapper
                    .eq("section_id",trackData.getPlanList().get(i).getSectionId())
                    .eq("row_type",trackData.getPlanList().get(i).getRowType())
                    .eq("line_id",lineId)
                    .eq("track_id",trackId);

            Track track = new Track();
            track.setPlan(trackData.getPlanList().get(i).getPlan());
            trackDao.update(track,queryWrapper);
            success++;

        }
        return success != 0 && trackData.getPlanList().size() != 0 && success == trackData.getPlanList().size();


    }
}
