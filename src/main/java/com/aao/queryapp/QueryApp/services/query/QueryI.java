package com.aao.queryapp.QueryApp.services.query;

import com.aao.queryapp.QueryApp.Entities.Models;
import java.util.*;

public interface QueryI {
    String createQuery(Models parentModel);
    String createQuery();
    String createQuery(Models parentModel, Collection<Models> childrenModels);
    String selectPhase(Models parentModel);
    String joinPhase(Models parentModel, Models childModel);
}
