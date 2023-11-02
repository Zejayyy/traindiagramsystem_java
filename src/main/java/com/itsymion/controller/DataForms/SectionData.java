package com.itsymion.controller.DataForms;


import com.itsymion.domain.Section;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SectionData
{

    private List<Section> records = new ArrayList<>();


}
