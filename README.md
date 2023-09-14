# community

## 项目描述
  一个基本功能完整的论坛项目。
  项目主要功能有:
  - 基于邮件激活的注册方式，基于MD5加密与加盐的密码存储方式。
  - 登录功能加入了随机验证码的验证。实现登录状态检查、为游客与已登录用户展示不同界面与功能。
  - 支持用户上传头像，实现发布帖子、评论帖子、发送私信与过滤敏感词等功能。实现了点赞，关注与系统通知功能。
    
## 技术选型
  SpringBoot + SSM + Redis + Kafka + ElasticSearch + SpringSecurity + Quartz + Caffeine

---
`出于安全考虑，仓库里的项目源文件中移除了生产环境的配置文件，使用前请自行将application.yml对于环境配置文件的引用更换为开发环境的配置文件application-develop.yml并对其中的参数进行适当调整。`
