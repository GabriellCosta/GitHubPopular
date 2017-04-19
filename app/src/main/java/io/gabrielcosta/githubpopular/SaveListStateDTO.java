package io.gabrielcosta.githubpopular;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gabrielcosta on 18/04/17.
 */

public class SaveListStateDTO implements Serializable {

  private final List<? extends Serializable> list;


  public SaveListStateDTO(List<? extends Serializable> list) {
    this.list = list;
  }

  public List<? extends Serializable> getList() {
    return list;
  }
}
