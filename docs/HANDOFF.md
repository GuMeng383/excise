# HANDOFF — 多人推理社交平台 / Excise

> 更新时间：2026-11-11。新会话先读本文件，不要 `read_thread` 翻旧会话。

## 1. 目标与验收标准

- 长期：把控制台推理游戏工程化成可展示的 Web 项目，2026-12-20 前有公网地址、3 人能在浏览器玩完一局（见 `Excise/docs/6周冲刺-1月跑起来.md`，本周为 W1 工程化收尾）。
- 当前阶段（第二阶段）：职责分层、接口抽象、单元测试。验收 = `mvn test` 全绿，且 `Main` 只负责组装与 I/O，业务规则不在 `Main` 里。
- 教学约定：用户自己写实现，助手只给接口/任务/提示；接口确认后再实现。

## 2. 已确定的决策与理由

1. **跳过 Socket 版直接上 Web**：Socket 版不产生可展示物，面试只能口头讲；Web 版能演示、能写简历。
2. **先手写服务分层，再上 Spring**：Spring 的 DI/IoC 就是这套思想的框架化，先手写理解"接口 + 构造器注入"再迁移，避免只会注解不懂原理。
3. **`GameService` 依赖 `GameResultHandler` 接口而非 `GameResultService` 具体类**：便于替换实现（测试替身），实现依赖倒置；`Main` 负责 `new GameService(new GameResultService())` 组装。
4. **构造器用 `Objects.requireNonNull`**：依赖缺失应在组装时立刻失败，而不是运行时 NPE 难定位。
5. **测试用测试替身（RecordingGameResultHandler）而不是真实结果服务**：要验证"接口方法被调用了"，不是"胜负算对了"。
6. **暂不升级 JDK 21**：当前 17 已验证可跑，避免本周同时改动运行时与分层；按 6 周计划在 W2 升 21 并拆多模块。

## 3. 当前状态

- 项目根：`D:\java\程序\多人推理社交平台`，Maven 工程在 `Excise`，包 `com.example.excise`。
- 主代码 `Excise\src\main\java\com\example\excise\`：`Main`、`Player`、`NormalPlayer`、`SpecialPlayer`、`Role`、`GameStatus`、`RoleInspector`、`Room`、`VotingService`、`PlayerComparators`、`PlayerService`、`RoomService`、`GameService`、`GameResultHandler`、`GameResultService`。
- 测试 `Excise\src\test\java\`：`GameServiceTest`、`GameResultServiceTest`、`PlayerServiceTest`、`PlayerTest`、`RoomTest`、`RoomPlayerTest`、`RoomServiceTest`、`RoomStartTest`、`VoteTest`。
- 已完成：创建玩家/房间/回合循环已从 `Main` 抽出到三个 Service；`GameResultHandler` 接口 + `GameResultService` 实现 + 构造器注入 + `Main` 组装；`GameServiceTest` 有"已结束房间不运行""整局能结束""传 null 抛 NPE"三条。
- **git 状态：无**。`Excise` 及其父目录都没有 `.git`，拿不到 commit，文件清单是当前盘点而非 diff。
- **测试状态：未核实**。环境无 `mvn`（只有 `java`），最近一次全量运行无法确认；`target/surefire-reports` 可能是旧的。

## 4. 验证方式

- 跑测试：IDEA 右侧 Maven → Lifecycle → `test`；或装了 Maven 后 `mvn -f "D:\java\程序\多人推理社交平台\Excise\pom.xml" test`。
- 跑游戏：IDEA 运行 `com.example.excise.Main`；命令行 `java -cp "Excise\target\classes" com.example.excise.Main`（先 `mvn compile`）。
- 判断"没坏"：测试全绿 + 手动玩一局走完 建房→加入→准备→开始→发言→投票→淘汰→胜负。
- 中文乱码：控制台 `chcp 65001`，IDEA 文件编码设 UTF-8；乱码不代表逻辑错。

## 5. 未决问题与下一步（按优先级）

1. **进行中**：`GameServiceTest` 里写 `RecordingGameResultHandler`（记录 `processResult` 是否被调用），注入 `GameService`，跑一回合断言"被调用"。
2. 抽 `ConsoleView`：把 `System.out` / `Scanner` 从 `Player`、`Room`、`VotingService`、`GameService` 里移出（W1 D4，本周最重要）。
3. `git init` + 补 `.gitignore`（`target/`、`.idea/`）+ 建 GitHub 仓库首次推送。
4. 修 Bug①：宽泛 `catch(Exception)` 吞掉 `NoSuchElementException` 导致死循环；`createRoom` 遇 EOF 崩溃。
5. 修 Bug②："最大人数必须大于3"提示与条件矛盾；`addPlayer` 改为返回 `boolean`。
6. `votes` 收进 `Room` 字段；删死代码（`SpecialPlayer`、`PlayerComparators`）；接 SLF4J，补平票/淘汰/四种胜负测试，JaCoCo > 70%。
7. W2：JDK 21 + 拆 `excise-core` / `excise-server` / `excise-console`。

## 6. 踩过的坑

- 测试里房间没 `startGame()`（状态仍是 WAITING），`GameService` 的 `while(PLAYING)` 不进入，断言像"没执行"。
- 房间 `startGame()` 后再 `addPlayer` 会被拒绝；测试要单独 new 一个 4 人房间，先加人再开始。
- 整局测试输入不足会 Scanner 耗尽 → `NoSuchElementException`；要么准备精确输入，要么只测单个服务。
- 测试不要用 `System.in`，用字符串构造的 `Scanner`。
- `Map.get()` 可能返回 null，用 `getOrDefault`；`Room.addPlayer()` 先判空再取属性。
- 输入数字应捕获 `NumberFormatException`，不要 `catch(Exception)`。
- 服务方法是实例方法，别用类名调用；`@Override` 非必须但推荐，能让编译器校验实现。
