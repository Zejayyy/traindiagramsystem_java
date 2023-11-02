package com.itsymion.controller.DataForms;


import com.itsymion.domain.SectionRuler;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SectionRulerData
{

    private List<SectionRuler> records = new ArrayList<>();
}
