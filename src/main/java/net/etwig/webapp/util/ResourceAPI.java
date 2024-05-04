package net.etwig.webapp.util;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface ResourceAPI <T>{

    public T add(Object... args);
    public T edit(Object... args);
    public T view(Object... args);
    public T remove(Object... args);
}
