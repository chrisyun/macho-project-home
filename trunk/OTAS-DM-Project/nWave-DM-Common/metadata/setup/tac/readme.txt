导入后, 如下型号没有TAC信息:
==================================================================================================
select f.manufacturer_external_id, m.manufacturer_model_id , m.name
from nw_dm_model m, nw_dm_manufacturer f 
where f.manufacturer_id=m.manufacturer_id and m.model_id not in (
  select model_id from nw_dm_model_tac
)
order by f.manufacturer_external_id, m.manufacturer_model_id
==================================================================================================
    MANUFACTURER_EXTERNAL_ID  MANUFACTURER_MODEL_ID NAME
1 Alcatel 156 156
2 Alcatel 512 512
3 Alcatel 715 715
4 Alcatel Elle  Elle
5 Alcatel OT301 OT301
6 Alcatel OT302 OT302
7 Alcatel OT303 OT303
8 Alcatel OT331 OT331
9 Alcatel OT331p  OT331p
10  Alcatel OT332a  OT332a
11  Alcatel OT501 OT501
12  Alcatel OT511 OT511
13  Alcatel OT701 OT701
14  Alcatel OTC550  OTC550
15  Alcatel OTC701  OTC701
16  Alcatel OTS853  OTS853
17  Alcatel OTVIEWDB  OT View db
18  Amoi  F90 F90
19  Asus  A632 MyPal  A632 MyPal
20  Asus  A636 MyPal  A636 MyPal
21  Asus  A639 MyPal  A639 MyPal
22  Asus  M530w M530w
23  Asus  P305  P305
24  Asus  P505  P505
25  Asus  P735  P735
26  BenQ-Siemens  C70 C70
27  BenQ-Siemens  C75 C75
28  BenQ-Siemens  CF75  CF75
29  BenQ-Siemens  CX70  CX70
30  BenQ-Siemens  CX75  CX75
31  BenQ-Siemens  E81 E81
32  BenQ-Siemens  E81v  E81v
33  BenQ-Siemens  M65 M65
34  BenQ-Siemens  M75 M75
35  BenQ-Siemens  ME75  ME75
36  BenQ-Siemens  S65 S65
37  BenQ-Siemens  S75 S75
38  BenQ-Siemens  SK65  SK65
39  BenQ-Siemens  SL65  SL65
40  BenQ-Siemens  SL75  SL75
41  BenQ-Siemens  SL91  SL91
42  BenQ-Siemens  SP65  SP65
43  BenQ-Siemens  SXG75 SXG75
44  Default Default Default
45  Dopod 515 515
46  Dopod 535 535
47  Dopod 565 565
48  Dopod 566 566
49  Dopod 575 575
50  Dopod 577W  577W
51  Dopod 585 585
52  Dopod 586 586
53  Dopod 586W  586W
54  Dopod 686 686
55  Dopod 696 696
56  Dopod 696i  696i
57  Dopod 700 700
58  Dopod 818 818
59  Dopod 818 Pro 818 Pro
60  Dopod 828 828
61  Dopod 838 Pro 838 Pro
62  Dopod 900 900
63  Dopod C500  C500
64  Dopod CHT 9000  CHT 9000
65  Dopod CHT 9100  CHT 9100
66  Dopod D810  D810
67  Dopod M700  M700
68  Dopod P800W P800W
69  Dopod U1000 U1000
70  Dopod s300  s300
71  ETEN  G500  G500
72  ETEN  G500+ G500+
73  ETEN  M500  M500
74  ETEN  M550  glofiish M550
75  ETEN  M600  M600
76  ETEN  M600+ M600+
77  ETEN  M700  glofiish M700
78  ETEN  M800  glofiish M800
79  ETEN  P300  P300
80  ETEN  P300B P300B
81  ETEN  P700  P700
82  ETEN  X500+ glofiish X500+
83  ETEN  X600  glofiish X600
84  ETEN  X800  glofiish X800
85  Ericsson  A2628 A2628
86  Ericsson  R320  R320
87  Ericsson  R380  R380
88  Ericsson  R380Titan R380Titan
89  Ericsson  R380World R380World
90  Ericsson  R380s R380s
91  Ericsson  R380sc  R380sc
92  Ericsson  R520  R520
93  Ericsson  R600  R600
94  Ericsson  T20 T20
95  Ericsson  T29 T29
96  Ericsson  T39 T39
97  Ericsson  T66 T66
98  HP  iPAQ hw6515 iPAQ hw6515
99  HP  iPAQ hw6900 iPAQ hw6900
100 HP  iPAQ hw6915 iPAQ hw6915
101 HP  iPAQ hx2190 iPAQ hx2190
102 HP  iPAQ hx2490 iPAQ hx2490
103 HP  iPAQ hx2790 iPAQ hx2790
104 HP  iPAQ rw6828 iPAQ rw6828
105 HP  iPAQ rx1950 iPAQ rx1950
106 HP  iPAQ rx5700 iPAQ rx5700
107 HP  iPAQ rx5900 iPAQ rx5900
108 HP  iPAQ-500  iPAQ-500
109 HTC 2125  2125
110 HTC 3100 (Star Trek)  3100 (Star Trek)
111 HTC 8500  8500
112 HTC 9090  9090
113 HTC Apache, PPC 6700  Apache, PPC 6700
114 HTC Artist  Artist
115 HTC Cingular 8125 Cingular 8125
116 HTC Elf, P3450  Elf, P3450
117 HTC Gemini  Gemini
118 HTC Hermes  Hermes
119 HTC MDA Compact MDA Compact
120 HTC MTeoR MTeoR
121 HTC P3350 P3350
122 HTC P3400 P3400
123 HTC P3600 P3600
124 HTC P3600i  P3600i
125 HTC P4550 P4550
126 HTC P6300 P6300
127 HTC P6500 P6500
128 HTC Prophet Prophet
129 HTC S411  S411
130 HTC S621  S621
131 HTC S630  S630
132 HTC S730  S730
133 HTC SDA SDA
134 HTC Shift Shift
135 HTC Touch Touch
136 HTC Touch Cruise  Touch Cruise
137 HTC Touch Dual  Touch Dual
138 HTC Trinity Trinity
139 HTC TyTN II TyTN II
140 HTC Wizard  Wizard
141 HTC XDA Trion XDA Trion
142 HTC v1605 v1605
143 HiSense W600  W600
144 Kyocera A1403K  A1403K
145 Kyocera A5515K  A5515K
146 Kyocera KX1 KX1
147 Kyocera KX324 KX324
148 Kyocera W41K  W41K
149 Kyocera W42K  W42K
150 Kyocera WX310k  WX310k
151 LG  1500  1500
152 LG  2000  2000
153 LG  5300I 5300I
154 LG  5400  5400
155 LG  7120  7120
156 LG  8138  8138
157 LG  A7150 A7150
158 LG  C1400 C1400
159 LG  C3100 C3100
160 LG  C3310 C3310
161 LG  F7250 F7250
162 LG  F7250T  F7250T
163 LG  G1610 G1610
164 LG  G1700 G1700
165 LG  G4020 G4020
166 LG  G635  G635
167 LG  G7030 G7030
168 LG  G7200 G7200
169 LG  KS10  KS10
170 LG  KS10 JOY  KS10 JOY
171 LG  KU730 KU730
172 LG  L3100 L3100
173 LG  L600v L600v
174 LG  LX350 LX350
175 LG  LX550 LX550
176 LG  MM535 MM535
177 LG  PM 325  PM 325
178 LG  PM225 PM225
179 LG  VI125 VI125
180 LG  VX8100  VX8100
181 LG  VX8300  VX8300
182 LG  VX9800  VX9800
183 Lenovo  E328  E328
184 Lenovo  E368  E368
185 Lenovo  P930  P930
186 Mitsubishi  D701i D701i
187 Mitsubishi  MusicPorter II  MusicPorter II
188 Mitsubishi  Trium 110 Trium 110
189 Mitsubishi  Trium Eclipse Trium Eclipse
190 Motorola  A1010 A1010
191 Motorola  A388  A388
192 Motorola  A768  A768
193 Motorola  C698p C698p
194 Motorola  E6  ROKR E6
195 Motorola  KRZRK1  KRZR K1
196 Motorola  KRZRK1m KRZR K1m
197 Motorola  KRZRK3  KRZR K3
198 Motorola  L71 L71
199 Motorola  MPX MPx
200 Motorola  MPX100  MPx100
201 Motorola  PEBLV6  PEBL V6
202 Motorola  Q8  Q 8
203 Motorola  Q9h Q 9h
204 Motorola  Q9q Q 9q
205 Motorola  RAZRMAXV6 RAZR MAX V6
206 Motorola  RAZRV6  RAZR V6
207 Motorola  RIZR Z10  RIZR Z10
208 Motorola  RIZR Z8 RIZR Z8
209 Motorola  RIZRZ3  RIZR Z3
210 Motorola  ROKER1  ROKER1
211 Motorola  SLVRL7  SLVR L7
212 Motorola  SLVRL72 SLVR L72
213 Motorola  SLVRL7e SLVR L7e
214 Motorola  SLVRL7i SLVR L7i
215 Motorola  SLVRL9  SLVR L9
216 Motorola  V1100 V1100
217 Motorola  V1150 V1150
218 Motorola  V150  V150
219 Motorola  V230  V230
220 Motorola  V270  V270
221 Motorola  V280  V280
222 Motorola  V360v V360v
223 Motorola  V361  V361
224 Motorola  V3c RAZR V3c
225 Motorola  V3e RAZR V3e
226 Motorola  V3m RAZR V3m
227 Motorola  V3t RAZR V3t
228 Motorola  V3x RAZR V3x
229 Motorola  V501  V501
230 Motorola  V557p V557p
231 Motorola  V6PEBL  V6PEBL
232 Motorola  V6V V6V
233 Motorola  V750  V750
234 Motorola  V8 iTunes V8 iTunes
235 Motorola  V8v V8v
236 NEC 705N  705N
237 NEC N150  N150
238 NOKIA 2855  2855
239 NOKIA 2865  2865
240 NOKIA 2865i 2865i
241 NOKIA 3300  3300
242 NOKIA 3530  3530
243 NOKIA 5500d 5500d
244 NOKIA 6020b 6020b
245 NOKIA 6121c 6121 classic
246 NOKIA 6124c 6124c
247 NOKIA 6131NFC 6131 NFC
248 NOKIA 6165  6165
249 NOKIA 6170b 6170b
250 NOKIA 6180  6180
251 NOKIA 6200IM  6200IM
252 NOKIA 6265  6265
253 NOKIA 6265i 6265i
254 NOKIA 6275  6275
255 NOKIA 6275i 6275i
256 NOKIA 6282  6282
257 NOKIA 6301  6301
258 NOKIA 6500  6500
259 NOKIA 6670b 6670b
260 NOKIA 6820a 6820a
261 NOKIA 6822a 6822a
262 NOKIA 6822b 6822b
263 NOKIA 8800arte  8800 Arte
264 NOKIA E51-1 E51-1
265 NOKIA E61-1 E61-1
266 NOKIA E61i-1  E61i-1
267 NOKIA E65-1 E65-1
268 NOKIA E66-1 E66-1
269 NOKIA E70-1 E70-1
270 NOKIA E71-1 E71-1
271 NOKIA E90-1 E90-1
272 NOKIA N71-1 N71-1
273 NOKIA N73-1 N73-1
274 NOKIA N76-1 N76-1
275 NOKIA N79 N79
276 NOKIA N81-1 N81 8GB
277 NOKIA N82-1 N82-1
278 NOKIA N85-1 N85
279 NOKIA N93-1 N93-1
280 NOKIA N95-1 N95-1
281 NOKIA N95-3 N95-3 NAM
282 NOKIA N96-3 N96-3
283 NOKIA ObservationCamera ObservationCamera
284 O2  Cosmo Cosmo
285 O2  Ice Ice
286 O2  X1b X1b
287 O2  X3  X3
288 O2  X7  X7
289 O2  XDA II mini XDA II mini
290 O2  XDA IIi XDA IIi
291 O2  XDA IIs XDA IIs
292 O2  Xda Argon XDA Argon
293 O2  Xda Comet XDA Comet
294 O2  Xda Exec  XDA Exec
295 O2  Xda Flame XDA Flame
296 O2  Xda Graphite  XDA Graphite
297 O2  Xda II  XDA II
298 O2  Xda Neo XDA Neo
299 O2  Xda Nova  XDA Nova
300 O2  Xda Orbit II  XDA Orbit II
301 O2  Xda Orion XDA Orion
302 O2  Xda Star  XDA Star
303 O2  Xda Stealth XDA Stealth
304 O2  Xda Stellar XDA Stellar
305 O2  Xda Terra XDA Terra
306 O2  Xda Trion XDA Trion
307 O2  Xda phone XDA phone
308 O2  Xphone IIm  Xphone IIm
309 Okwap K728  K728
310 Panasonic GD35  GD35
311 Panasonic GD67  GD67
312 Panasonic GD75  GD75
313 Panasonic GD95  GD95
314 Panasonic GD96  GD96
315 Panasonic VS2 VS2
316 Panasonic VS3 VS3
317 Panasonic VS6 VS6
318 Panasonic X100  X100
319 Panasonic X11 X11
320 Panasonic X200  X200
321 Panasonic X300  X300
322 Panasonic X400  X400
323 Panasonic X500  X500
324 Panasonic X68 X68
325 Panasonic X700  X700
326 Panasonic X701  X701
327 Panasonic X77 X77
328 Panasonic X800  X800
329 Panasonic X88 X88
330 Philips 650 650
331 Philips Fisio 312 Fisio 312
332 Philips Fisio 820 Fisio 820
333 Qtek  1010  1010
334 Qtek  2020  2020
335 Qtek  2020i 2020i
336 Qtek  7070  7070
337 Qtek  8010  8010
338 Qtek  8020  8020
339 Qtek  8060  8060
340 Qtek  8080  8080
341 Qtek  8100  8100
342 Qtek  8300  8300
343 Qtek  8310  8310
344 Qtek  8500  8500
345 Qtek  8600  8600
346 Qtek  9000  9000
347 Qtek  9090  9090
348 Qtek  9100  9100
349 Qtek  9600  9600
350 Qtek  S100  S100
351 Qtek  S110  S110
352 Qtek  S200  S200
353 RIM BlackBerry 6280 BlackBerry 6280
354 RIM BlackBerry 7100g  BlackBerry 7100g
355 RIM BlackBerry 7130c  BlackBerry 7130c
356 RIM BlackBerry 8700r  BlackBerry 8700r
357 Sagem MY401C  MY401C
358 Sagem MY411V  MY411V
359 Sagem MY800V  MY800V
360 Sagem MY850v  MY850v
361 Sagem MYX52 MYX52
362 Sagem VS2 VS2
363 Sagem VS4 VS4
364 Sagem myC5-2 Vodafone MYC52V
365 Sagem myV-5 MYV56
366 Sagem myX-6-2 myX62
367 Samsung A411  SGH-A411
368 Samsung A711  SGH-A711
369 Samsung A801  SGH-A801
370 Samsung A821  SGH-A821
371 Samsung C200C SGH-C200C
372 Samsung C200S SGH-C200S
373 Samsung D500C SGHD-500C
374 Samsung E330N SGH-E330N
375 Samsung E340E SGH-E340E
376 Samsung E348  SGH-E348
377 Samsung E570v SGH-E570v
378 Samsung E600C SGH-E600C
379 Samsung E610C SGH-E610C
380 Samsung E618  SGH-E618
381 Samsung E620C SGH-E620C
382 Samsung E720C SGH-E720C
383 Samsung E800N SGH-E800N
384 Samsung E820N SGH-E820N
385 Samsung E820P SGH-E820P
386 Samsung E860  SGH-E860
387 Samsung F308  SGH-F308
388 Samsung F700V SGH-F700V
389 Samsung I300  I300
390 Samsung I300x I300x
391 Samsung I310  I310
392 Samsung I320  I320
393 Samsung I320N I320N
394 Samsung I710  I710
395 Samsung I718  I718
396 Samsung I750  I750
397 Samsung I780  I780
398 Samsung IP-830w IP-830w
399 Samsung IP-A790 IP-A790
400 Samsung L400v SGH-L400v
401 Samsung L760v SGH-L760v
402 Samsung MM-A700 MM-A700
403 Samsung P710  SGH-P710
404 Samsung P716  SGH-P716
405 Samsung P730C SGH-P730C
406 Samsung PM-A740 PM-A740
407 Samsung SCH-i600  SCH-i600
408 Samsung SGH-J600v SGH-J600v
409 Samsung T529  SGH-T529
410 Samsung U608  SGH-U608
411 Samsung U700V SGH-U700V
412 Samsung U708  SGH-U708
413 Samsung X480C SGH-X480C
414 Samsung X640S SGH-X640S
415 Samsung X660V SGH-X660V
416 Samsung Z105M SGH-Z105M
417 Samsung Z105T SGH-Z105T
418 Samsung Z105U SGH-Z105U
419 Samsung Z107V SGH-Z107V
420 Samsung Z110  SGH-Z110
421 Samsung Z140M SGH-Z140M
422 Samsung Z140N SGH-Z140N
423 Samsung Z400V SGH-Z400V
424 Samsung Z510  SGH-Z510
425 Samsung Z520V SGH-Z520V
426 Samsung ZV50  SGH-ZV50
427 Samsung ZV560 SGH-ZV560
428 Samsung i520  SGH-i520
429 Samsung i550v SGH-i550v
430 Samsung i560v SGH-i560v
431 Samsung i601  SGH-i601
432 Samsung i607  BlackJack
433 Samsung i620  SGH-i620
434 Samsung i620v SGH-i620v
435 Samsung i640v SGH-i640v
436 Samsung i700  I700
437 Sanyo MM 7400 MM 7400
438 Sendo S330I S330I
439 Sendo S601  S601
440 Sendo X X
441 Sharp 7035SHV 7035SHV
442 Sharp 705SH 705SH
443 Sharp 810SH 810SH
444 Sharp 811SH 811SH
445 Sharp 902SHV  902SHV
446 Sharp 904SH 904SH
447 Sharp 910SH 910SH
448 Sharp TM-100  TM-100
449 Sharp TM-200  TM-200
450 Siemens 1168  1168
451 Siemens 3508i 3508i
452 Siemens 3618  3618
453 Siemens 6618  6618
454 Siemens 6686i 6686i
455 Siemens C65V  C65V
456 Siemens C66 C66
457 Siemens C70 C70
458 Siemens C72V  C72V
459 Siemens C75V  C75V
460 Siemens CF62T CF62T
461 Siemens CT70  CT70
462 Siemens CT71  CT71
463 Siemens CV65  CV65
464 Siemens CV70  CV70
465 Siemens CV71  CV71
466 Siemens CV75  CV75
467 Siemens CX65V CX65V
468 Siemens CX70E CX70E
469 Siemens CX70V CX70V
470 Siemens CXT65 CXT65
471 Siemens CXT70 CXT70
472 Siemens CXV65 CXV65
473 Siemens EL71  EL71
474 Siemens M65v  M65v
475 Siemens S35 S35
476 Siemens S45 S45
477 Siemens S65V  S65V
478 Siemens SL42  SL42
479 Siemens SL65Escada  SL65Escada
480 Siemens SX1McLaren  SX1McLaren
481 SonyEricsson  J200c J200c
482 SonyEricsson  J210c J210c
483 SonyEricsson  J230c J230c
484 SonyEricsson  J300a J300a
485 SonyEricsson  J300c J300c
486 SonyEricsson  K310c K310c
487 SonyEricsson  K500c K500c
488 SonyEricsson  K506c K506c
489 SonyEricsson  K508c K508c
490 SonyEricsson  K510c K510c
491 SonyEricsson  K530  K530
492 SonyEricsson  K550c K550c
493 SonyEricsson  K550im  K550im
494 SonyEricsson  K610c K610c
495 SonyEricsson  K610im  K610im
496 SonyEricsson  K630  K630
497 SonyEricsson  K660  K660
498 SonyEricsson  K700c K700c
499 SonyEricsson  K750a K750a
500 SonyEricsson  K750c K750c
501 SonyEricsson  K758c K758c
502 SonyEricsson  K770  K770
503 SonyEricsson  K790c K790c
504 SonyEricsson  K800c K800c
505 SonyEricsson  K810c K810c
506 SonyEricsson  K850c K850c
507 SonyEricsson  M600c M600c
508 SonyEricsson  P1c P1c
509 SonyEricsson  P1i P1i
510 SonyEricsson  P900  P900
511 SonyEricsson  P908  P908
512 SonyEricsson  P910  P910
513 SonyEricsson  P910c P910c
514 SonyEricsson  P990c P990c
515 SonyEricsson  S700a S700a
516 SonyEricsson  S700c S700c
517 SonyEricsson  T102  T102
518 SonyEricsson  T105  T105
519 SonyEricsson  T202  T202
520 SonyEricsson  T226s T226s
521 SonyEricsson  T238  T238
522 SonyEricsson  T250i T250i
523 SonyEricsson  T306  T306
524 SonyEricsson  T316  T316
525 SonyEricsson  T602  T602
526 SonyEricsson  T61z  T61z
527 SonyEricsson  T628  T628
528 SonyEricsson  T650c T650c
529 SonyEricsson  T68m  T68m
530 SonyEricsson  V630i V630i
531 SonyEricsson  V640  V640
532 SonyEricsson  W300c W300c
533 SonyEricsson  W380a W380a
534 SonyEricsson  W380c W380c
535 SonyEricsson  W380i W380i
536 SonyEricsson  W550c W550c
537 SonyEricsson  W580c W580c
538 SonyEricsson  W610c W610c
539 SonyEricsson  W700c W700c
540 SonyEricsson  W710a W710a
541 SonyEricsson  W710c W710c
542 SonyEricsson  W810a W810a
543 SonyEricsson  W810c W810c
544 SonyEricsson  W830i W830i
545 SonyEricsson  W850a W850a
546 SonyEricsson  W850c W850c
547 SonyEricsson  W880c W880c
548 SonyEricsson  W888  W888
549 SonyEricsson  W890c W890c
550 SonyEricsson  W900c W900c
551 SonyEricsson  W918c W918c
552 SonyEricsson  W950c W950c
553 SonyEricsson  Z208  Z208
554 SonyEricsson  Z300c Z300c
555 SonyEricsson  Z310a Z310a
556 SonyEricsson  Z310c Z310c
557 SonyEricsson  Z500c Z500c
558 SonyEricsson  Z500i Z500i
559 SonyEricsson  Z525a Z525a
560 SonyEricsson  Z530c Z530c
561 SonyEricsson  Z550a Z550a
562 SonyEricsson  Z550c Z550c
563 SonyEricsson  Z608  Z608
564 SonyEricsson  Z610c Z610c
565 SonyEricsson  Z710c Z710c
566 SonyEricsson  Z710i Z710i
567 SonyEricsson  Z750  Z750
568 TCL e787  e787
569 Tatung  M1A M1A
570 Telit sp1000  sp1000
571 Telit sp2000  sp2000
572 Toshiba G500  G500
573 Toshiba G900 PPC  G900 PPC
574 Toshiba Neon  Neon
575 Toshiba TS921 TS921
576 Toshiba V910T V910T
577 Toshiba W21T  W21T
578 Toshiba W32T  W32T
579 Toshiba W44T  W44T
580 VKMobile  VK530 VK530
581 VKMobile  VK570 VK570
582 ZTE F150  F150
583 i-mate  JAM Black JAM Black
584 i-mate  JAMA 101  JAMA 101
585 i-mate  JAMA 201  JAMA 201
586 i-mate  JAMin JAMin
587 i-mate  JAQ JAQ
588 i-mate  JAQ3  JAQ3
589 i-mate  JAQ4  JAQ4
590 i-mate  JASJAM  JASJAM
591 i-mate  K-JAM K-JAM
592 i-mate  PDA2  PDA2
593 i-mate  PDA2k PDA2k
594 i-mate  Pocket PC Pocket PC
595 i-mate  SP3i  SP3i
596 i-mate  SP4m  SP4m
597 i-mate  SP5m  SP5m
598 i-mate  SPJAS SPJAS
599 i-mate  Smartflip Smartflip
600 i-mate  Smartphone  Smartphone
601 i-mate  Smartphone2 Smartphone2
602 i-mate  Ultimate 5150 Ultimate 5150
603 i-mate  Ultimate 6150 Ultimate 6150
604 i-mate  Ultimate 7150 Ultimate 7150
605 i-mate  Ultimate 8150 Ultimate 8150
606 i-mate  Ultimate 8502 Ultimate 8502
607 i-mate  Ultimate 9150 Ultimate 9150
608 i-mate  Ultimate 9502 Ultimate 9502
==================================================================================================
