package com.xhpcd.server.session;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/10/13:22
 * @Description:
 */
@Data
@AllArgsConstructor
public class Group {

    private String name;
    private Set<String> members;
}
