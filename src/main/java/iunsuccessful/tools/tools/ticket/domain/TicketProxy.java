//package iunsuccessful.tools.tools.ticket.domain;
//
//import javax.persistence.*;
//import java.io.Serializable;
//
///**
// * id	int	11	0	0	0	0	0	0		0					-1	0
// ip	varchar	255	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0
// port	int	10	0	-1	0	0	0	0		0					0	0
// invalid	int	11	0	-1	0	0	0	0		0	无效次数，超过三次，就干掉				0	0
// last_effective_time	int	11	0	-1	0	0	0	0		0	最后有效时间				0	0
// last_run_time	int	11	0	-1	0	0	0	0		0	最后执行时间				0	0
//
// * Created by LiQZ on 2017/6/27.
// */
//@Entity
//@Table(name = "ticket_proxy")
//public class TicketProxy implements Serializable {
//
//    private static final long serialVersionUID = 2017630195618L;
//
//    @Id
//    @GeneratedValue
//    private Integer id;
//
//    @Column
//    private String ip;
//
//    @Column
//    private Integer port;
//
//    /**
//     * 失效次数，超过三次归零
//     */
//    @Column
//    private Integer invalid;
//
//    private Integer lastEffectiveTime;
//
//    @Column
//    private Integer lastRunTime;
//
//
//}
