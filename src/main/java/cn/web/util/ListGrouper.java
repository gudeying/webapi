package cn.web.util;

import java.util.ArrayList;
import java.util.List;

public class ListGrouper
{
  public <T> List<List<T>> groupListByQuantity(List<T> list, int quantity)
  {
    if ((list == null) || (list.size() == 0)) {
      return null;
    }
    if (quantity <= 0) {
      new IllegalArgumentException("Wrong quantity.");
    }
    List<List<T>> wrapList = new ArrayList();
    int count = 0;
    while (count < list.size())
    {
      wrapList.add(new ArrayList(list.subList(count, count + quantity > list.size() ? list.size() : count + quantity)));
      count += quantity;
    }
    return wrapList;
  }
}
