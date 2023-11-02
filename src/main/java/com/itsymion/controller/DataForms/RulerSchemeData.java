package com.itsymion.controller.DataForms;

import com.itsymion.domain.RulerScheme;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class RulerSchemeData
{

    private List<RulerScheme> records = new ArrayList<>();
}
