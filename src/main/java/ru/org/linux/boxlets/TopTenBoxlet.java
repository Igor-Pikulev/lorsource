/*
 * Copyright 1998-2016 Linux.org.ru
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package ru.org.linux.boxlets;

import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.org.linux.site.Template;
import ru.org.linux.topic.TopTenDao;
import ru.org.linux.topic.TopTenDao.TopTenMessageDTO;
import ru.org.linux.topic.Topic;
import ru.org.linux.user.Profile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class TopTenBoxlet extends AbstractBoxlet {
  @Autowired
  private TopTenDao topTenDao;

  @Override
  @RequestMapping("/top10.boxlet")
  protected ModelAndView getData(HttpServletRequest request) {
    Profile profile = Template.getTemplate().getProf();

    List<TopTenMessageDTO> list = topTenDao.getMessages();

    list.forEach(dto -> dto.setPages(Topic.getPageCount(dto.getCommentCount(), profile.getMessages())));

    return new ModelAndView("boxlets/top10", ImmutableMap.of("messages", list));
  }
}
